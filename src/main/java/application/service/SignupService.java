package application.service;

import application.dto.UserSignupRequest;
import application.entity.users.Users;

public interface SignupService {

     Users signup(UserSignupRequest userSignupRequest);
}
