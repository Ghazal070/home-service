package application.entity.users;


import application.entity.Credit;
import application.entity.Order;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@DiscriminatorValue("Customer")
public class Customer extends Users {


    @OneToOne
    @Cascade({CascadeType.PERSIST})
    private Credit credit;

    @OneToMany(mappedBy = "customer")
    private Set<Order> orders = new HashSet<>();
    @Override
    public String toString() {
        return super.toString();
    }


}
