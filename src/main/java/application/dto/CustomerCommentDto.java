package application.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerCommentDto implements Serializable {



    private String content;

    @NotNull
    private Integer offerId;

    @NotNull
    @Min(value = 1)
    @Max(value = 5)
    private Integer score;
}
