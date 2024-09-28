package application.service.impl;

import application.dto.DutyCreation;
import application.entity.Duty;
import application.entity.enumeration.ExpertStatus;
import application.entity.users.Admin;
import application.entity.users.Expert;
import application.service.DutyService;
import application.service.ExpertService;
import jakarta.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import application.repository.AdminRepository;
import application.service.AdminService;
import application.service.PasswordEncode;
import application.util.AuthHolder;

import java.util.Optional;
import java.util.Set;

public class AdminServiceImpl extends UserServiceImpl<AdminRepository, Admin>
        implements AdminService {


    private final DutyService dutyService;
    private final ExpertService expertService;

    public AdminServiceImpl(AdminRepository repository, AuthHolder authHolder, PasswordEncode passwordEncode, DutyService dutyService, ExpertService expertService) {
        super(repository, authHolder, passwordEncode);
        this.dutyService = dutyService;
        this.expertService = expertService;
    }

    @Override
    public Duty createDuty(DutyCreation dutyCreation) {
        //done parentTitle+title is uniq
        //done exist duty boolean instead of load duty
        //done optional instead of ==null
        Optional<Duty> optionalParentDuty = dutyCreation.getParentId() != null ?
                Optional.of(dutyService.findById(dutyCreation.getParentId())) : Optional.empty();
        if (dutyCreation.getParentId() != null && !optionalParentDuty.isPresent()) {
            throw new ValidationException("This parent duty does not exist.");
        }
        if (optionalParentDuty.isPresent()) {
            Duty parentDuty = optionalParentDuty.get();
            if (!dutyService.containByUniqField(dutyCreation.getTitle(),parentDuty.getId())) {
                throw new ValidationException("Title for duty already exists for this parent duty.");
            }
            return createDutyWithParent(dutyCreation, parentDuty);
        }
        return createDutyWithoutParent(dutyCreation);
    }

    private Duty createDutyWithParent(DutyCreation dutyCreation, Duty parentDuty) {
        Duty buildDuty = Duty.builder()
                .title(dutyCreation.getTitle())
                .parent(parentDuty)
                .basePrice(dutyCreation.getBasePrice())
                .description(dutyCreation.getDescription())
                .selectable(dutyCreation.getSelectable())
                .build();
        return dutyService.save(buildDuty);
    }

    private Duty createDutyWithoutParent(DutyCreation dutyCreation) {
        Duty buildDuty = Duty.builder()
                .title(dutyCreation.getTitle())
                .selectable(dutyCreation.getSelectable())
                .build();
        return dutyService.save(buildDuty);
    }

    @Override
    public Boolean updateExpertStatus(Integer expertId) {
        if (expertId != null) {
            //done only ExpertStatus.Accepted no get all expertStatus
            Expert expert = expertService.findById(expertId);
            if (expert.getExpertStatus().equals(ExpertStatus.New)) {
                expert.setExpertStatus(ExpertStatus.Accepted);
                expertService.update(expert);
                return true;
            } else throw new ValidationException("ExpertStatus does not New");
        } else throw new ValidationException("Expert does not exist");

    }

    @Override
    public Boolean addDutyToExpert(Integer expertId, Integer dutyId) {
        //done--- find duty and expert
        Expert expert = expertService.findById(expertId);
        Duty duty = dutyService.findById(dutyId);
        if (expert == null || duty == null) {
            throw new ValidationException("Expert or duty is null");
        }
        if (!duty.getSelectable()) {
            throw new ValidationException("Duty selectable is false");
        }
        if (expertService.havePermissionExpertToServices(expert)) {
            Set<Duty> duties = expert.getDuties();
            if (duties.add(duty)) {
                expertService.update(expert);
                return true;
            }
        } else throw new ValidationException("Duty is no selectable");
        return false;
    }

    @Override
    public Boolean removeDutyFromExpert(Integer expertId, Integer dutyId) {
        //done remove with query no for
        Expert expert = expertService.findById(expertId);
        Duty duty = dutyService.findById(dutyId);
        if (expert == null || duty == null) {
            throw new ValidationException("Expert or duty is null");
        }
        Set<Duty> duties = expert.getDuties();
        if (duties != null && !duties.isEmpty()) {
            if (!duties.remove(duty)) throw new ValidationException("Duty does not exist in set expert");
        } else throw new ValidationException("Duty set is empty");
        expertService.update(expert);
        return true;
    }
}
