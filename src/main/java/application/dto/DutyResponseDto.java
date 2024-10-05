package application.dto;

import lombok.*;

import java.io.Serializable;

@Builder
public record DutyResponseDto(
        Integer id,String title, Integer basePrice,
        Boolean selectable,DutyResponseDto parent) implements Serializable {
}
