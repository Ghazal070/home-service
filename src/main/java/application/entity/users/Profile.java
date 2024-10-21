package application.entity.users;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Builder
public class Profile {

    @Column(unique = true)
    //@Email
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z]+(?:.[a-zA-Z]+)*$",message = "Please follow this pattern: gh@to.com")
    private String email;

    @Column
    //@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{8}$", message = "Password must be include 8 characters long and contain at least one letter and one number")
    private String password;

    @Override
    public String toString() {
        return "email='" + email +'\'';
    }
}
