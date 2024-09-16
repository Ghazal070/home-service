package entity.users.factory;

import dto.UserSignupRequest;
import entity.enumeration.ExpertStatus;
import entity.users.Expert;
import entity.users.Profile;
import entity.users.User;

import java.time.ZonedDateTime;

public class ExpertFactory implements UserFactory {
    @Override
    public User createUser(UserSignupRequest userSignupRequest) {
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
