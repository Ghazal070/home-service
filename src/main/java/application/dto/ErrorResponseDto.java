package application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class ErrorResponseDto implements Serializable {

    private String dateError;
    private String message;
    private String status;


}
