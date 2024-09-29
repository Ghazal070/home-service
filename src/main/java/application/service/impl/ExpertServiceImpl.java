package application.service.impl;

import application.entity.enumeration.ExpertStatus;
import application.entity.users.Expert;
import application.repository.ExpertRepository;
import application.service.ExpertService;
import application.service.PasswordEncode;
import application.util.AuthHolder;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

@Service
public class ExpertServiceImpl extends UserServiceImpl<ExpertRepository, Expert> implements ExpertService {
    public ExpertServiceImpl(Validator validator, ExpertRepository repository, AuthHolder authHolder, PasswordEncode passwordEncode) {
        super(validator, repository, authHolder, passwordEncode);
    }

    @Override
    public Boolean havePermissionExpertToServices(Expert expert) {
        if (expert != null) {
            if (expert.getExpertStatus().equals(ExpertStatus.Accepted)) {
                return true;
            }
        }
        return false;
    }
}
