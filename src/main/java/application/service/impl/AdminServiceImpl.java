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
        if(StringUtils.isNotBlank(dutyTypeTitle)){
            return dutyTypeService.save(DutyType.builder()
                    .title(dutyTypeTitle)
                    .build());
        }
        throw new ValidationException("dutyTypeTitle is not blank");
    }

    @Override
    public Duty createDuty(DutyCreation dutyCreation) {
        //todo criteriaBuilder
        String titleDutyType = dutyCreation.getTitleDutyType();
        Duty parentDuty=null;
        DutyType dutyType = dutyTypeService.findByUniqId(titleDutyType);
        List<DutyType> dutyTypes = dutyTypeService.loadAll();
        boolean dutyTypeIsExist = false;
        boolean dutyParentTitleIsExist = false;
        if (dutyTypes!=null && !dutyTypes.isEmpty()){
            for (int i = 0; i < dutyTypes.size(); i++) {
                if (dutyTypes.get(i).getTitle().equalsIgnoreCase(titleDutyType)){
                    dutyTypeIsExist=true;
                    break;
                }
            }
        }

        if (StringUtils.isNotBlank(dutyCreation.getParentTitle())){
             parentDuty= dutyService.findByParentTitle(dutyCreation.getParentTitle());
        }
        if(dutyType!=null && dutyTypeIsExist &&parentDuty!=null){
            return dutyService.save(Duty.builder()
                    .dutyType(dutyType)
                    .parent(parentDuty)
                    .basePrice(dutyCreation.getBasePrice())
                    .description(dutyCreation.getDescription())
                    .build());
        } else if (dutyType!=null && dutyTypeIsExist ) {
            return dutyService.save(Duty.builder()
                    .dutyType(dutyType)
                    .build());

        } else throw new ValidationException("title duty type must be from above list. for new duty type please createDutyType");
    }
}
