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

    @NotNull(message = "Title must not be null")
    @Column(unique = true)
    private  String title;
    @ManyToOne
    private Duty parent;

    @Column
    private Integer basePrice;

    @Column
    private String description;

    @PrePersist
    @PreUpdate
    private  void validator(){
        if (parent!=null){
            if (parent.getId() != null) {
                if (basePrice == null || description == null) {
                    ApplicationContext.getLogger().info("Parent Id is not null must not be null description and basePrice");
                    throw new ValidationException("Parent Id is not null must not be null description and basePrice");
                }
            }
        }
    }
    @PostPersist
    private void createLog(){
        ApplicationContext.getLogger().info("Duty " + this.title + " is created");
    }


    @Override
    public String toString() {
        return parent!=null? id+"- " +"title=" + title + ", parentId=" + parent.getId() + ", basePrice=" + basePrice + ", description='" + description + '\'':
                id+"- " +"title=" + title + ", parentId=" + "null"+ ", basePrice=" + basePrice + ", description='" + description + '\'';

    }
}
