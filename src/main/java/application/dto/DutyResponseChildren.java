package application.dto;

import application.entity.Duty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DutyResponseChildren {


    private Integer id;
    private String title;

    private String parentTitle;

    private Boolean selectable;

    private Set<DutyResponseChildren> subDuty = new HashSet<>();


    @Override
    public String toString() {
        if (parentTitle == null) {
            return id + "- " + "title=" + title + ", selectable=" + selectable +", subDuty=" + subDuty;
        } else return id + "- " + "title=" + title + ", selectable=" + selectable;
    }
}
