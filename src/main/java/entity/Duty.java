package entity;

import entity.enumeration.DutyType;
import exception.ValidationException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import util.ApplicationContext;

import java.util.logging.Logger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class Duty extends  BaseEntity<Integer> {

    @Enumerated(EnumType.STRING)
    @NotNull
    private DutyType dutyType;

    @Column
    private Integer parentId;

    @Column
    private Integer basePrice;

    @Column
    private String description;

    @PrePersist
    @PreUpdate
    private  void validator(){
        if (parentId != null ){
            if (basePrice==null || description==null){
                ApplicationContext.getLogger().info("Parent Id is not null must not be null description and basePrice");
                throw  new ValidationException("Parent Id is not null must not be null description and basePrice");
            }
        }
    }
    @PostPersist
    private void createLog(){
        ApplicationContext.getLogger().info("Duty " + this.dutyType + " is created");
    }


    @Override
    public String toString() {
        return id+"- " +"dutyType=" + dutyType + ", parentId=" + parentId + ", basePrice=" + basePrice + ", description='" + description + '\'';

    }
}
