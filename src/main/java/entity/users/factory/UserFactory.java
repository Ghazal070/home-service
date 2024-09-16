package entity.users.factory;

import dto.UserSignupRequest;
import entity.users.User;

public interface UserFactory {

    User createUser(UserSignupRequest userSignupRequest);
}
