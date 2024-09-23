package application.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSignupRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String pathImage;
    private String role;

    @Override
    public String toString() {
        return "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='" + email + '\'' + ", password='" + password + '\'' +", role=" + role +
                ", pathImage='" + pathImage + '\'';
    }
}
