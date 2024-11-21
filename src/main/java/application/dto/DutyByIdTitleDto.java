package application.dto;

import lombok.Builder;

import java.io.Serializable;

@Builder
public record DutyByIdTitleDto(
        Integer id,String title) implements Serializable {
}
