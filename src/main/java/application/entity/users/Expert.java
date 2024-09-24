package application.entity.users;


import application.entity.Duty;
import application.entity.enumeration.ExpertStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@DiscriminatorValue("Expert")
public class Expert extends Users {

    @Enumerated(EnumType.STRING)
    private ExpertStatus expertStatus;

    @Column
    @Builder.Default
    private Integer score = 0;

    @ManyToMany
    private Set<Duty> duties = new HashSet<>();

    @Override
    public String toString() {
        return super.toString()+" expertStatus=" + expertStatus;
    }
}
