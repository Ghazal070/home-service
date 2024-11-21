package application.entity.users.userFactory;

import application.dto.UserSignupRequestDto;
import application.entity.users.Users;

public interface UserFactory {

    Users createUser(UserSignupRequestDto userSignupRequestDto);
}
