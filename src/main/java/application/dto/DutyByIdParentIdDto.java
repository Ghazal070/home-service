package application.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record DutyByIdParentIdDto(
        Integer id, Integer parentId) implements Serializable {
}
