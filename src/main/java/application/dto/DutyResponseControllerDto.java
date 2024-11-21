package application.dto;

import lombok.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DutyResponseControllerDto implements Serializable {

    private String title;

    private Set<DutyResponseControllerDto> subDuty=new HashSet<>();

}
