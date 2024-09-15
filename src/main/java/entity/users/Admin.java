package entity.users;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
@Entity
@SuperBuilder
@DiscriminatorValue("Admin")
public class Admin extends User{


}
