package application.entity.users.userFactory;

import application.constants.RoleNames;
import application.dto.UserSignupRequestDto;
import application.entity.Credit;
import application.entity.enumeration.ExpertStatus;
import application.entity.users.Expert;
import application.entity.users.Profile;
import application.entity.users.Users;
import application.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ExpertFactory implements UserFactory {
    private final RoleService roleService;
    @Override
    public Users createUser(UserSignupRequestDto userSignupRequestDto) {
        Expert expert = Expert.builder()
                .firstName(userSignupRequestDto.getFirstName())
                .lastName(userSignupRequestDto.getLastName())
                .profile(
                        Profile.builder()
                                .email(userSignupRequestDto.getEmail())
                                .password(userSignupRequestDto.getPassword())
                                .build()
                )
                .dateTimeSubmission(LocalDateTime.now())
                .credit(Credit.builder().amount(0.0).build())
                .expertStatus(ExpertStatus.New)
                .roles(Set.of(roleService.findByName(RoleNames.EXPERT).get()))
                .build();
        expert.setImage(userSignupRequestDto.getInputStream());
        return expert;
    }
}
