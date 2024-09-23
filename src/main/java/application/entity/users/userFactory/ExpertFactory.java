package application.entity.users.userFactory;

import application.dto.UserSignupRequest;
import application.entity.enumeration.ExpertStatus;
import application.entity.users.Expert;
import application.entity.users.Profile;
import application.entity.users.Users;

import java.time.ZonedDateTime;

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
                .dateTimeSubmission(ZonedDateTime.now())
                .expertStatus(ExpertStatus.New)
                .build();
        expert.getBytes(userSignupRequest.getPathImage());
        return expert;
    }
}
