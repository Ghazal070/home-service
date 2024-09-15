package service;

import dto.UserSignupRequest;
import entity.users.User;

public interface SignupService {

     User signup(UserSignupRequest userSignupRequest);
}
