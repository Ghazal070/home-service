package application.entity.users;


import application.entity.Credit;
import application.entity.Duty;
import application.entity.Offer;
import application.entity.Order;
import application.entity.enumeration.ExpertStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

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

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Duty> duties = new HashSet<>();

    @OneToOne
    @Cascade({CascadeType.PERSIST})
    private Credit credit;

    @OneToMany(mappedBy = "expert")
    private Set<Offer> offers = new HashSet<>();

    @Override
    public String toString() {
        return super.toString()+" expertStatus=" + expertStatus;
    }
}
