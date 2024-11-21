package application.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity


public class Credit extends BaseEntity<Integer> {

    @Column
    @NotNull
    @Min(value = 0)
    @Builder.Default
    private Double amount=0.0;
}
