package application.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record DutyByIdDto(
        Integer id, Integer parentId) implements Serializable {
}
