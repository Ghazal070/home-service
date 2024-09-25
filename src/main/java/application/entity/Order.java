package application.entity;


import application.entity.enumeration.OrderStatus;
import application.entity.users.Customer;
import application.entity.users.Users;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder

public class Order extends BaseEntity<Integer> {

    @Column
    @NotNull
    private String description;

    @Column
    @NotNull
    private Integer priceOrder;

    @Column
    @Future
    @NotNull
    private ZonedDateTime dateTimeOrder;

    @Column
    private String address;

    @Enumerated
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.ExpertOfferWanting;

    @ManyToOne
    @NotNull
    private Customer customer;

    @ManyToOne
    @NotNull
    private Duty duty;

    @OneToMany(mappedBy = "order")
    private Set<Offer> offers;


}
