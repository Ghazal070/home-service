package entity.users;


import entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class User extends BaseEntity<Integer> {

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\\\.[a-zA-Z0-9-]+)*$",message = "Please follow thit pattern: gh@to.com")
    private String email;

    @Column
    private String password;

    @Column
    private ZonedDateTime dateTimeSubmission;

    @Column
    private Byte [] image;






}
