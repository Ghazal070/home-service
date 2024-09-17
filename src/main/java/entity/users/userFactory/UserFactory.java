package entity.users.userFactory;

import dto.UserSignupRequest;
import entity.users.Users;

public interface UserFactory {

    Users createUser(UserSignupRequest userSignupRequest);
}
