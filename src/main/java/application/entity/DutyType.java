package application.entity;

import jakarta.persistence.Entity;
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
public class DutyType extends BaseEntity<Integer> {

    private  String title;

    @Override
    public String toString() {
        return "title='" + title + '\'';
    }
}
