package application.dto;

import application.exception.ValidationException;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DutyCreation {


    @NotNull
    @NotBlank
    private String title;

    private Integer parentId;

    private Integer basePrice;

    private String description;

    @NotNull
    private Boolean selectable;

    @PrePersist
    @PreUpdate
    private  void validator(){
        if (parentId != null ){
            if (basePrice==null || description==null){
                throw  new ValidationException("Parent Id is not null must not be null description and basePrice");
            }
        }
    }

}
