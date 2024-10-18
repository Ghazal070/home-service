package application.entity;


import application.entity.users.Expert;
import jakarta.persistence.*;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
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
    private LocalDateTime dateTimeOffer;

    @Column
    @NotNull
    private LocalDateTime dateTimeStartWork;


    @Column
    @Min(value = 1 , message = "Min days for days working is 1")
    @NotNull
    private Integer lengthDays;


    @PrePersist
    protected void onCreate(){
        if (dateTimeStartWork.isBefore(LocalDateTime.now())){
            throw new ValidationException("DateTimeStartWork offer must be after now localDateTime in persist");
        }
        this.dateTimeOffer =LocalDateTime.now();
    }



}
