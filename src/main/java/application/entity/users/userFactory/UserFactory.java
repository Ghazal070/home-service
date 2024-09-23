package application.entity.users.userFactory;

import application.dto.UserSignupRequest;
import application.entity.users.Users;

public interface UserFactory {

    Users createUser(UserSignupRequest userSignupRequest);
}
