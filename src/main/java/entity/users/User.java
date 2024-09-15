package entity.users;


import entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;
import java.util.Arrays;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public class User extends BaseEntity<Integer> {

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Embedded
    private  Profile profile;

    @Column
    private ZonedDateTime dateTimeSubmission;

    @Column
    private Byte [] image;


    @Override
    public String toString() {
        return id+"- " + "firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + profile + ", dateTimeSubmission=" + dateTimeSubmission + ", image=" + Arrays.toString(image);
    }
}
