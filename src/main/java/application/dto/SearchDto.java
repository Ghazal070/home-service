package application.dto;


import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.ValidationException;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchDto implements Serializable {

    private String userRole;

    private String firstName;

    private String lastName;

    private String email;

    private Set<Integer> dutyId;

    private Integer minScore;
    private Integer maxScore;


}
