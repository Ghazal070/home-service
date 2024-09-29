package application.entity.users.userFactory;

import application.dto.UserSignupRequest;
import application.entity.enumeration.ExpertStatus;
import application.entity.users.Expert;
import application.entity.users.Profile;
import application.entity.users.Users;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Component
public class ExpertFactory implements UserFactory {
    @Override
    public Users createUser(UserSignupRequest userSignupRequest) {
        Expert expert = Expert.builder()
                .firstName(userSignupRequest.getFirstName())
                .lastName(userSignupRequest.getLastName())
                .profile(
                        Profile.builder()
                                .email(userSignupRequest.getEmail())
                                .password(userSignupRequest.getPassword())
                                .build()
                )
                .dateTimeSubmission(LocalDateTime.now())
                .expertStatus(ExpertStatus.New)
                .build();
        expert.setImage(userSignupRequest.getInputStream());
        //done inputStream in dto
        return expert;
    }
}
