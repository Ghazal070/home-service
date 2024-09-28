package application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.InputStream;
import java.io.InputStreamReader;

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
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8}$", message = "Password must be include 8 characters long and contain at least one letter and one number")
    private String password;

    @NotNull
    private InputStream inputStream;

    @NotNull
    private String role;

    @Override
    public String toString() {
        return "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='" + email + '\'' + ", password='" + password + '\'' +", role=" + role;
    }
}
