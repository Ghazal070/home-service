package application.service.impl;

import application.dto.DutyCreation;
import application.entity.Duty;
import application.entity.users.Admin;
import application.service.DutyService;
import jakarta.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import application.repository.AdminRepository;
import application.service.AdminService;
import application.service.PasswordEncode;
import application.util.AuthHolder;

import java.util.List;

public class AdminServiceImpl extends UserServiceImpl<AdminRepository, Admin>
        implements AdminService {


    private final DutyService dutyService;

    public AdminServiceImpl(AdminRepository repository, AuthHolder authHolder, PasswordEncode passwordEncode, DutyService dutyService) {
        super(repository, authHolder, passwordEncode);
        this.dutyService = dutyService;
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
                    .build();
        } else if (dutyCreation.getParentTitle() != null && parentDuty == null) {
            throw new ValidationException("This parent duty is not exist");
        } else {
            buildDuty = Duty.builder()
                    .title(titleDuty)
                    .build();
        }
        return dutyService.save(buildDuty);
    }
}
