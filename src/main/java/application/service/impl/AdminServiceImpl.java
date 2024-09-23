package application.service.impl;

import application.entity.DutyType;
import application.entity.users.Admin;
import jakarta.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import application.repository.AdminRepository;
import application.service.AdminService;
import application.service.DutyTypeService;
import application.service.PasswordEncode;
import application.util.AuthHolder;

public class AdminServiceImpl extends UserServiceImpl<AdminRepository, Admin>
        implements AdminService {

    private final DutyTypeService dutyTypeService;
    public AdminServiceImpl(AdminRepository repository, AuthHolder authHolder, PasswordEncode passwordEncode, DutyTypeService dutyTypeService) {
        super(repository, authHolder, passwordEncode);
        this.dutyTypeService = dutyTypeService;
    }

    @Override
    public DutyType createDutyType(String dutyTypeTitle) {
        if(StringUtils.isNotBlank(dutyTypeTitle)){
            return dutyTypeService.save(DutyType.builder()
                    .title(dutyTypeTitle)
                    .build());
        }
        throw new ValidationException("dutyTypeTitle is not blank");
    }
}
