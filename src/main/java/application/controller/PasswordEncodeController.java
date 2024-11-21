package application.controller;

import application.dto.PasswordRequestDto;
import application.exception.ValidationControllerException;
import application.service.impl.PasswordEncoderImpl;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class PasswordEncodeController {
    private final PasswordEncoderImpl passwordEncodeService;

    @GetMapping("/passwords/encode")
    public ResponseEntity<String> encoding(@RequestBody String password) {
        String encode = passwordEncodeService.encode(password);
        return encode!=null ? ResponseEntity.ok("Password encode") :
                new ResponseEntity<>("Encoding is not supported", HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/passwords/verify")
    public ResponseEntity<String> isEqualEncodeDecodePass(@RequestBody PasswordRequestDto passwordRequestDto) {
        try{
            Boolean pass = passwordEncodeService.isEqualEncodeDecodePass(passwordRequestDto.getOldPassword(),
                    passwordRequestDto.getEncodedPassword());
            return pass ? ResponseEntity.ok("Password is equal encoded") :
                    new ResponseEntity<>("Password is not equal encoded", HttpStatus.BAD_REQUEST);
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }




}
