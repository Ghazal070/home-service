package application.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateDuty {

    @NotNull
    @NotBlank
    private Integer dutyId;

    private Integer price;

    private String description;

    private Boolean selectable;
}
