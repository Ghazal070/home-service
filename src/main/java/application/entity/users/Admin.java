package application.entity.users;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.stereotype.Component;

@Getter
@Setter
@AllArgsConstructor
//@NoArgsConstructor
@Entity
@SuperBuilder
@DiscriminatorValue("Admin")
@Component
public class Admin extends Users {


}
