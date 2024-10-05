package application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateDutyDto {

    @NotNull
    @NotBlank
    private Integer dutyId;


    private Integer price;

    private String description;

    private Boolean selectable;
}
