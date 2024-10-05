package application.service;

import application.dto.UserSignupRequestDto;
import application.entity.users.Users;

public interface SignupService {

     Users signup(UserSignupRequestDto userSignupRequestDto);
}
