package application.service.impl;

import application.constants.RoleNames;
import application.dto.DutyCreationDto;
import application.dto.SearchDto;
import application.dto.UsersSearchResponse;
import application.entity.Duty;
import application.entity.enumeration.ExpertStatus;
import application.entity.users.*;
import application.jwt.JwtService;
import application.service.*;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import application.repository.AdminRepository;
import application.util.AuthHolder;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl extends UserServiceImpl<AdminRepository, Admin>
        implements AdminService {


    private final DutyService dutyService;
    private final ExpertService expertService;
    private final UserSpecification userSpecification;
    private final RoleService roleService;

    public AdminServiceImpl(Validator validator, AdminRepository repository, AuthHolder authHolder, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService, DutyService dutyService, ExpertService expertService, UserSpecification userSpecification, RoleService roleService) {
        super(validator, repository, authHolder, passwordEncoder, authenticationManager, jwtService);
        this.dutyService = dutyService;
        this.expertService = expertService;
        this.userSpecification = userSpecification;
        this.roleService = roleService;
    }

    @SneakyThrows
    @PostConstruct
    public void init() {
        if (repository.count() == 0) {
            String rawPassword = "admin123";
            Admin admin = Admin.builder()
                    .firstName("admin")
                    .lastName("admin")
                    .profile(
                            Profile.builder()
                                    .email("admin@admin.com")
                                    .password(passwordEncoder.encode(rawPassword))
                                    .build()
                    )
                    .dateTimeSubmission(LocalDateTime.now())
                    .image(new Byte[]{100, 120})
                    .roles(Set.of(
                            roleService.findByName(
                                    RoleNames.ADMIN)
                                    .get()))
                    .build();
            repository.save(admin);
        }
    }

    @Override
    public Duty createDuty(DutyCreationDto dutyCreationDto) {
        Duty parentDuty = null;
        if (dutyCreationDto.getParentId() != null) {
            parentDuty = dutyService.findById(dutyCreationDto.getParentId())
                    .orElseThrow(() -> new ValidationException("This parent duty does not exist."));
            if (dutyService.containByUniqField(dutyCreationDto.getTitle(), parentDuty.getId())) {
                throw new ValidationException("Title for duty already exists for this parent duty.");
            }
        } else {
            if (dutyService.existsByTitle(dutyCreationDto.getTitle())) {
                throw new ValidationException("Title exists for this parent null.");
            }
        }
        return dutyService.save(dutyCreation(dutyCreationDto, parentDuty));
    }

    private Duty dutyCreation(DutyCreationDto dutyCreationDto, Duty parentDuty) {
        Duty dutyBuilder = Duty.builder()
                .title(dutyCreationDto.getTitle())
                .basePrice(dutyCreationDto.getBasePrice())
                .description(dutyCreationDto.getDescription())
                .selectable(dutyCreationDto.getSelectable())
                .build();
        if (parentDuty != null) {
            dutyBuilder.setParent(parentDuty);
        }
        return dutyBuilder;
    }

    @Override
    public Boolean updateExpertStatus(Integer expertId) {
        Optional<Expert> expert = expertService.findById(expertId);
        if (expert.get().getExpertStatus().equals(ExpertStatus.AcceptWaiting)) {
            expert.get().setExpertStatus(ExpertStatus.Accepted);
            expertService.update(expert.get());
            return true;
        } else throw new ValidationException("ExpertStatus does not New");
    }

    @Override
    public Boolean addDutyToExpert(Integer expertId, Integer dutyId) {
        Optional<Expert> expert = expertService.findById(expertId);
        Optional<Duty> duty = dutyService.findById(dutyId);
        if (expert.isEmpty() || duty.isEmpty()) {
            throw new ValidationException("Expert or duty is null");
        }
        if (!duty.get().getSelectable()) {
            throw new ValidationException("Duty selectable is false");
        }
        if (expertService.havePermissionExpertToServices(expert.get().getId())) {
            Set<Duty> duties = expert.get().getDuties();
            if (duties.add(duty.get())) {
                expertService.update(expert.get());
                return true;
            }
        } else throw new ValidationException("Expert isn't Accept status");
        return false;
    }

    @Override
    @Transactional
    public Boolean removeDutyFromExpert(Integer expertId, Integer dutyId) {
        Expert expert = expertService.findById(expertId)
                .orElseThrow(() -> new ValidationException("Expert not found with ID: " + expertId));
        Duty duty = dutyService.findById(dutyId)
                .orElseThrow(() -> new ValidationException("Duty not found with ID: " + dutyId));
        Set<Duty> duties = expert.getDuties();
        if (duties == null || duties.isEmpty()) {
            throw new ValidationException("Duty set is empty for expert with ID: " + expertId);
        }
        boolean removed = duties.remove(duty);
        if (!removed) {
            throw new ValidationException("Duty with ID " + dutyId + " does not exist in expert's duty set");
        }
        expertService.update(expert);
        return true;
    }

    @Override
    public List<UsersSearchResponse> searchUser(SearchDto searchDto) {
        List<Users> users = userSpecification.findAllBySearchDto(searchDto);
        List<UsersSearchResponse> usersResponse = new ArrayList<>();
        users.forEach(u -> {
                    UsersSearchResponse response = UsersSearchResponse.builder()
                            .email(u.getProfile().getEmail())
                            .dateTimeSubmission(u.getDateTimeSubmission())
                            .firstName(u.getFirstName())
                            .lastName(u.getLastName())
                            .enabled(u.getEnabled())
                            .build();
                    if (u instanceof Expert expert) {
                        response.setScore((expert.getScore()));
                        Set<Duty> duties = expert.getDuties();
                        Set<String> stringList = duties.stream().map(d -> d.getTitle()).collect(Collectors.toSet());
                        response.setDuties(stringList);
                        response.setExpertStatus(expert.getExpertStatus());
                    }
                    usersResponse.add(response);
                }
        );
        return usersResponse;
    }
}
