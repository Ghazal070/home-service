package entity.users;


import entity.enumeration.ExpertStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

    @Override
    public String toString() {
        return super.toString()+" expertStatus=" + expertStatus;
    }
}
