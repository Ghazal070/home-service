package application.entity;


import application.exception.ValidationException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import application.util.ApplicationContext;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class Duty extends  BaseEntity<Integer> {

    @NotNull(message = "DutyType must not be null")
    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
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
