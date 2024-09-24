package application.service.impl;

import application.entity.enumeration.ExpertStatus;
import application.entity.users.Expert;
import application.repository.ExpertRepository;
import application.service.ExpertService;
import application.service.PasswordEncode;
import application.util.AuthHolder;

public class ExpertServiceImpl extends UserServiceImpl<ExpertRepository, Expert> implements ExpertService {
    public ExpertServiceImpl(ExpertRepository repository, AuthHolder authHolder, PasswordEncode passwordEncode) {
        super(repository, authHolder, passwordEncode);
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
