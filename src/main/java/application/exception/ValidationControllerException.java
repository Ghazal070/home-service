package application.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class ValidationControllerException extends RuntimeException{

    private final String message;
    private final HttpStatus httpStatus;
}
