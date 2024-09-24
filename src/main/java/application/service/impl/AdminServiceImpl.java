package application.service.impl;

import application.dto.DutyCreation;
import application.entity.Duty;
import application.entity.DutyType;
import application.entity.users.Admin;
import application.service.DutyService;
import jakarta.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import application.repository.AdminRepository;
import application.service.AdminService;
import application.service.DutyTypeService;
import application.service.PasswordEncode;
import application.util.AuthHolder;

import java.util.List;

public class AdminServiceImpl extends UserServiceImpl<AdminRepository, Admin>
        implements AdminService {

    private final DutyTypeService dutyTypeService;
    private final DutyService dutyService;
    public AdminServiceImpl(AdminRepository repository, AuthHolder authHolder, PasswordEncode passwordEncode, DutyTypeService dutyTypeService, DutyService dutyService) {
        super(repository, authHolder, passwordEncode);
        this.dutyTypeService = dutyTypeService;
        this.dutyService = dutyService;
    }

    @Override
    public DutyType createDutyType(String dutyTypeTitle) {
        DutyType dutyType = dutyTypeService.findByUniqId(dutyTypeTitle);
        if(dutyType==null && StringUtils.isNotBlank(dutyTypeTitle)){
            return dutyTypeService.save(DutyType.builder()
                    .title(dutyTypeTitle)
                    .build());
        } else if (!StringUtils.isNotBlank(dutyTypeTitle)) {
            throw new ValidationException("DutyTypeTitle is not blank");
        }else
            throw new ValidationException("DutyTypeTitle is Duplicate");
    }

    @Override
    public Duty createDuty(DutyCreation dutyCreation) {
        //todo criteriaBuilder
        String titleDutyType = dutyCreation.getTitleDutyType();
        Duty parentDuty=null;
        DutyType dutyType = dutyTypeService.findByUniqId(titleDutyType);
        if (StringUtils
                .isNotBlank(dutyCreation.getParentTitle())){
             parentDuty= dutyService.findByParentTitle(dutyCreation.getParentTitle());
        }
        if(dutyType!=null && parentDuty!=null){
            return dutyService.save(Duty.builder()
                    .dutyType(dutyType)
                    .parent(parentDuty)
                    .basePrice(dutyCreation.getBasePrice())
                    .description(dutyCreation.getDescription())
                    .build());
        } else if (dutyType!=null) {
            return dutyService.save(Duty.builder()
                    .dutyType(dutyType)
                    .build());

        } else throw new ValidationException("This duty type does not exist.For new duty type please createDutyType");
    }
}
