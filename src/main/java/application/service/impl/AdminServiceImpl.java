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
        String titleDuty = dutyCreation.getTitle();
        Duty parentDuty = null;
        Duty buildDuty;
        Duty duty = dutyService.findByUniqId(titleDuty);
        if (StringUtils
                .isNotBlank(dutyCreation.getParentTitle())) {
            parentDuty = dutyService.findByUniqId(dutyCreation.getParentTitle());
        }
        if (duty != null) {
            throw new ValidationException("Title for duty is exist");
        }

        if (parentDuty != null) {
            buildDuty = Duty.builder()
                    .title(titleDuty)
                    .parent(parentDuty)
                    .basePrice(dutyCreation.getBasePrice())
                    .description(dutyCreation.getDescription())
                    .selectable(dutyCreation.getSelectable())
                    .build();
        } else if (dutyCreation.getParentTitle() != null && parentDuty == null) {
            throw new ValidationException("This parent duty is not exist");
        } else {
            buildDuty = Duty.builder()
                    .title(titleDuty)
                    .selectable(dutyCreation.getSelectable())
                    .build();
        }
        return dutyService.save(buildDuty);
    }

    @Override
    public Boolean updateExpertStatus(Expert expert, ExpertStatus expertStatus) {
        if (expert != null) {
            if (expert.getExpertStatus().equals(ExpertStatus.New)) {
                expert.setExpertStatus(expertStatus);
                expertService.update(expert);
                return true;
            } else throw new ValidationException("ExpertStatus does not New");
        }
        else throw new ValidationException("Expert does not exist");

    }

    @Override
    public Boolean addDutyToExpert(Expert expert, Duty duty) {
        if (expert == null || duty == null) {
            throw new ValidationException("Expert or duty is null");
        }
        if (!duty.getSelectable()){
            throw new ValidationException("Duty selectable is false");
        }

        if (!expertService.havePermissionExpertToServices(expert)) {
            updateExpertStatus(expert, ExpertStatus.Accepted);
        }
        if (expertService.havePermissionExpertToServices(expert)) {
            Set<Duty> duties = expert.getDuties();
            if (duties != null && !duties.isEmpty()) {
                for (Duty existDuty : duties) {
                    if (existDuty.equals(duty)) {
                        throw new ValidationException("Duty is duplicate and exist in set expert");
                    }
                }
            }
            boolean addDuty = duties.add(duty);
            expertService.update(expert);
            return addDuty;
        }
        return false;

    }

    @Override
    public Boolean removeDutyFromExpert(Expert expert, Duty duty) {
        boolean removeDuty = false;
        if (expert == null || duty == null) {
            throw new ValidationException("Expert or duty is null");
        }
        if (expertService.havePermissionExpertToServices(expert)) {
            Set<Duty> duties = expert.getDuties();
            if (duties != null && !duties.isEmpty()) {
                //todo?? stream convert to id check id equal has better performance?
                for (Duty existDuty : duties) {
                    if (existDuty.equals(duty)) {
                        removeDuty = duties.remove(duty);
                    }
                }
                if (!removeDuty)throw new ValidationException("Duty does not exist in set expert");
            }
            expertService.update(expert);
        }
        return removeDuty;
    }
}
