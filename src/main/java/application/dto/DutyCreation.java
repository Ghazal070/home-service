package application.dto;

import application.exception.ValidationException;
import application.util.ApplicationContext;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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

    private String parentTitle;

    private Integer basePrice;

    private String description;

    private Boolean selectable;

    @PrePersist
    @PreUpdate
    private  void validator(){
        if (parentTitle != null ){
            if (basePrice==null || description==null){
                ApplicationContext.getLogger().info("Parent Id is not null must not be null description and basePrice");
                throw  new ValidationException("Parent Id is not null must not be null description and basePrice");
            }
        }
    }

}
