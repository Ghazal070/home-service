package application.entity;


import application.entity.users.Expert;
import application.exception.ValidationException;
import jakarta.persistence.*;
import jakarta.validation.Valid;
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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"title", "parent_id"}))
@SuperBuilder
public class Duty extends BaseEntity<Integer> {

    @NotNull(message = "Title must not be null")
    @Column
    private String title;
    @ManyToOne
    @Valid
    private Duty parent;

    @Column
    private Integer basePrice;

    @Column
    private String description;

    @OneToMany(mappedBy = "parent")
    private Set<Duty> subDuty = new HashSet<>();

    @ManyToMany(mappedBy = "duties")
    private Set<Expert> experts = new HashSet<>();

    @Column
    @NotNull
    private Boolean selectable;

    @PrePersist
    @PreUpdate
    private void validator() {
        if (selectable) {
            if (basePrice == null || description == null) {
                ApplicationContext.getLogger().info("Parent Id is not null must not be null description and basePrice");
                throw new ValidationException("Parent Id is not null must not be null description and basePrice");
            }
        }
    }

    @PostPersist
    private void createLog() {
        ApplicationContext.getLogger().info("Duty " + this.title + " is created");
    }


    @Override
    public String toString() {
        if (parent != null) {
            return id + "- " + "title=" + title + ", parentId=" + parent.getId() + ", subDuty=" + subDuty + ", basePrice=" + basePrice + ", description='" + description + '\'' + ", selectable=" + selectable;
        } else return id + "- " + "title=" + title + ", selectable=" + selectable + ", subDuty=" + subDuty;
    }
}
