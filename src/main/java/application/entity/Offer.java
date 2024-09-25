package application.entity;


import application.entity.users.Expert;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.ZonedDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class Offer extends BaseEntity<Integer>{

    @ManyToOne
    @NotNull
    private Order order;

    @ManyToOne
    @NotNull
    private Expert expert;

    @Column
    @NotNull
    private Integer priceOffer;

    @Column
    @NotNull
    private ZonedDateTime dateTimeOffer;

    @Column
    @Future
    @NotNull
    private ZonedDateTime dateTimeStartWork;


    @Column
    @Min(value = 1 , message = "Min days for days working is 1")
    @NotNull
    private Integer lengthDays;




}
