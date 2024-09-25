package application.dto;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateDuty {

    private String title;
    private Integer price;
    private String description;
    private Boolean selectable;
}
