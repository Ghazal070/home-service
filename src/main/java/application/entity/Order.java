package application.entity;


import application.entity.enumeration.OrderStatus;
import application.entity.users.Customer;
import application.entity.users.Expert;
import application.entity.users.Users;
import jakarta.persistence.*;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders-home-service")
@SuperBuilder

public class Order extends BaseEntity<Integer> {

    @Column
    @NotNull
    private String description;

    @Column
    @NotNull
    private Integer priceOrder;

    @Column
    @NotNull
    private LocalDateTime dateTimeOrderForDoingFromCustomer;

    @Column
    private String address;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.ExpertOfferWanting;

    @ManyToOne
    @NotNull
    private Customer customer;

    @ManyToOne
    private Expert expert;

    @ManyToOne
    @NotNull
    private Duty duty;

    @OneToMany(mappedBy = "order")
    private Set<Offer> offers;

    @Column
    private LocalDateTime doneUpdate;

    @PrePersist
    protected void onCreate(){
        if (dateTimeOrderForDoingFromCustomer.isBefore(LocalDateTime.now())){
            throw new ValidationException("DateTimeOrderForDoingFromCustomer order must be after now localDateTime in persist");
        }
    }

    @Override
    public String toString() {
        return "id='" + id +"description='" + description + '\'' + ", priceOrder=" + priceOrder + ", dateTimeOrderForDoingFromCustomer=" + dateTimeOrderForDoingFromCustomer + ", address='" + address + '\''
                + ", orderStatus=" + orderStatus + ", customer=" + customer + ", duty=" + duty;
    }
}
