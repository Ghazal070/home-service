package application.entity;


import application.exception.ValidationException;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;
import application.util.ApplicationContext;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
public class Duty extends BaseEntity<Integer> {

    @NotNull(message = "Title must not be null")
    @Column(unique = true)
    private String title;
    @ManyToOne
    private Duty parent;

    @Column
    private Integer basePrice;

    @Column
    private String description;

    @OneToMany(mappedBy = "parent")
    private Set<Duty> subDuty = new HashSet<>();

    @PrePersist
    @PreUpdate
    private void validator() {
        if (parent != null) {
            if (parent.getId() != null) {
                if (basePrice == null || description == null) {
                    ApplicationContext.getLogger().info("Parent Id is not null must not be null description and basePrice");
                    throw new ValidationException("Parent Id is not null must not be null description and basePrice");
                }
            }
        }
    }

    @PostPersist
    private void createLog() {
        ApplicationContext.getLogger().info("Duty " + this.title + " is created");
    }


    @Override
    public String toString() {
        if (parent != null && !subDuty.isEmpty()) {
            return id + "- " + "title=" + title + ", parentId=" + parent.getId() + ", subDuty=" + subDuty + ", basePrice=" + basePrice + ", description='" + description + '\'';
        } else if (parent != null)
            return id + "- " + "title=" + title + ", parentId=" + parent.getId() + ", basePrice=" + basePrice + ", description='" + description + '\'';
        else if (parent == null && !subDuty.isEmpty()) {
            return id + "- " + "title=" + title + ", subDuty=" + subDuty;
        } else return id + "- " + "title=" + title;
    }
}
