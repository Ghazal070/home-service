package dto;

import entity.enumeration.Role;
import jakarta.persistence.Column;
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
    private Byte [] image;
    private Role role;

}
