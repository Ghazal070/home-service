package entity.users;


import entity.enumeration.ExpertStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Expert extends User{

    @Enumerated(EnumType.STRING)
    private ExpertStatus expertStatus;
}
