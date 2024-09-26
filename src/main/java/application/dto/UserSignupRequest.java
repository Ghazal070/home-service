package application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSignupRequest {

    private String firstName;
    private String lastName;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String pathImage;

    @NotNull
    private String role;

    @Override
    public String toString() {
        return "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='" + email + '\'' + ", password='" + password + '\'' +", role=" + role +
                ", pathImage='" + pathImage + '\'';
    }
}
