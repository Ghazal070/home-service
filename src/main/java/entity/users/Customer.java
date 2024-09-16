package entity.users;


import entity.users.User;
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
@DiscriminatorValue("Customer")
public class Customer extends User {

    @Override
    public String toString() {
        return super.toString();
    }
}
