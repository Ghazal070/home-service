package application.dto;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DutyResponseChildrenDto {


    private Integer id;
    private String title;

    private String parentTitle;

    private Boolean selectable;

    private Set<DutyResponseChildrenDto> subDuty = new HashSet<>();


    @Override
    public String toString() {
        if (parentTitle == null) {
            return id + "- " + "title=" + title + ", selectable=" + selectable +", subDuty=" + subDuty;
        } else return id + "- " + "title=" + title + ", selectable=" + selectable;
    }
}
