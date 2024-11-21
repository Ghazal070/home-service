package application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateDutyDto  implements Serializable {

    @NotNull
    @NotBlank
    private Integer dutyId;


    private Integer price;

    private String description;

    private Boolean selectable;
}
