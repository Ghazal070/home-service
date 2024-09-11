package entity;

import entity.enumeration.DutyType;
import exception.CustomException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
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
                throw  new CustomException();
            }
        }
    }

}
