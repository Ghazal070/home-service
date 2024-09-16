package service.impl;

import dto.UserSignupRequest;
import entity.users.User;
import entity.users.factory.UserFactory;
import service.SignupService;
import service.UserService;

public class SignupServiceImpl implements SignupService {

    private final UserService userService;
    private final UserFactory userFactory;

    public SignupServiceImpl(UserService userService, UserFactory userFactory) {
        this.userService = userService;
        this.userFactory = userFactory;
    }

    @Override
    public User signup(UserSignupRequest userSignupRequest) {
        User user = userFactory.createUser(userSignupRequest);
        if (user != null) {
//            user = userService.save(user)

        }
        return user;
    }
}
