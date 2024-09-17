package service;

import dto.UserSignupRequest;
import entity.users.Users;

public interface SignupService {

     Users signup(UserSignupRequest userSignupRequest);
}
