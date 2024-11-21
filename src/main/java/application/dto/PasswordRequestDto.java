package application.dto;

import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PasswordRequestDto implements Serializable {

    private String oldPassword;

    private String encodedPassword;
}
