package application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserChangePasswordDto  implements Serializable {


    @NotNull
    @NotBlank
    private String oldPassword;

    @NotNull
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8}$", message = "Password must be include 8 characters long and contain at least one letter and one number")
    private String newPassword;
}
