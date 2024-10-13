package application.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Card extends BaseEntity<Integer> {

    @Column(unique = true)
    @NotNull
    @Pattern(regexp = "^\\d{4}-\\d{4}$")
    private String cardNumber;

    @Column
    @NotNull
    @Future
    private LocalDateTime localDateTime;

    @Column
    @NotNull
    private String bankName;

    @Column
    @NotNull
    @Pattern(regexp = "^\\{4d}$")
    private String ccv2;

    @Column
    @NotNull
    @Pattern(regexp = "^\\{4d}$")
    private String secondPassword;

    @Column
    @NotNull
    private Integer amountCard;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Card card = (Card) o;
        return Objects.equals(cardNumber, card.cardNumber) && Objects.equals(localDateTime, card.localDateTime) && Objects.equals(bankName, card.bankName) && Objects.equals(ccv2, card.ccv2) && Objects.equals(secondPassword, card.secondPassword);
    }
}
