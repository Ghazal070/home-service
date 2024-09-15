package entity.users;


import entity.enumeration.ExpertStatus;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SuperBuilder
@DiscriminatorValue("Expert")
public class Expert extends User{

    @Enumerated(EnumType.STRING)
    private ExpertStatus expertStatus;

    @Override
    public String toString() {
        return "expertStatus=" + expertStatus;
    }
}
