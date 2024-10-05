package application.service.impl;

import application.dto.DutyCreation;
import application.entity.Duty;
import application.entity.enumeration.ExpertStatus;
import application.entity.users.Admin;
import application.entity.users.Expert;
import application.service.DutyService;
import application.service.ExpertService;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import application.repository.AdminRepository;
import application.service.AdminService;
import application.service.PasswordEncodeService;
import application.util.AuthHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class AdminServiceImpl extends UserServiceImpl<AdminRepository, Admin>
        implements AdminService {


    private final DutyService dutyService;
    private final ExpertService expertService;

    public AdminServiceImpl(Validator validator, AdminRepository repository, AuthHolder authHolder, PasswordEncodeService passwordEncodeService, DutyService dutyService, ExpertService expertService) {
        super(validator, repository, authHolder, passwordEncodeService);
        this.dutyService = dutyService;
        this.expertService = expertService;
    }

    @Override
    public Duty createDuty(DutyCreation dutyCreation) {
        Duty parentDuty = null;
        if (dutyCreation.getParentId() != null) {
            parentDuty = dutyService.findById(dutyCreation.getParentId())
                    .orElseThrow(() -> new ValidationException("This parent duty does not exist."));
            if (dutyService.containByUniqField(dutyCreation.getTitle(), parentDuty.getId())) {
                throw new ValidationException("Title for duty already exists for this parent duty.");
            }
        } else {
            if (dutyService.existsByTitle(dutyCreation.getTitle())) {
                throw new ValidationException("Title exists for this parent null.");
            }
        }
        return dutyService.save(dutyCreation(dutyCreation, parentDuty));
    }

    private Duty dutyCreation(DutyCreation dutyCreation, Duty parentDuty) {
        Duty dutyBuilder = Duty.builder()
                .title(dutyCreation.getTitle())
                .basePrice(dutyCreation.getBasePrice())
                .description(dutyCreation.getDescription())
                .selectable(dutyCreation.getSelectable())
                .build();
        if (parentDuty != null) {
            dutyBuilder.setParent(parentDuty);
        }
        return dutyBuilder;
    }

    @Override
    public Boolean updateExpertStatus(Integer expertId) {
            Optional<Expert> expert = expertService.findById(expertId);
            if (expert.get().getExpertStatus().equals(ExpertStatus.New)) {
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
        if (expertService.havePermissionExpertToServices(expert.get())) {
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
}
