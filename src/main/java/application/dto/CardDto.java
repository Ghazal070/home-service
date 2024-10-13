package application.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardDto implements Serializable {

    @NotNull
    @Pattern(regexp = "^\\d{4}-\\d{4}$")
    private String cardNumber;

    @NotNull
    private String localDateTime;

    @NotNull
    private String bankName;

    @NotNull
    @Pattern(regexp = "^\\{4d}$")
    private String ccv2;

    @NotNull
    @Pattern(regexp = "^\\{4d}$")
    private String secondPassword;
}
