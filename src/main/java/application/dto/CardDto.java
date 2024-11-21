package application.dto;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
    private String localDate;

    @NotNull
    private String bankName;

    @NotNull
    @Pattern(regexp = "^\\d{4}$")
    private String ccv2;

    @NotNull
    @Pattern(regexp = "^\\d{4}$")
    private String secondPassword;

    @NotBlank
    @Size(min = 5, max = 5)
    private String captcha;
}
