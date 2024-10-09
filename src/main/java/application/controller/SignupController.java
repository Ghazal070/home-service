package application.controller;

import application.dto.UserSignupRequestDto;
import application.entity.users.Users;
import application.exception.ValidationControllerException;
import application.service.SignupService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
public class SignupController {

    private final SignupService signupService;
    private final ObjectMapper objectMapper;

    @PostMapping(value = "/createUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> signup(
            @RequestPart("user") @Valid String userJson,
            @RequestPart("image") MultipartFile imageFile) {
        try {
//            ObjectMapper objectMapper = new ObjectMapper();
            UserSignupRequestDto userSignupRequestDto = objectMapper.readValue(userJson, UserSignupRequestDto.class);
            InputStream inputStream = imageFile.getInputStream();
            userSignupRequestDto.setInputStream(inputStream);

            Users users = signupService.signup(userSignupRequestDto);
            return users != null ? ResponseEntity.ok("Signup successful") :
                    new ResponseEntity<>("Signup failed", HttpStatus.BAD_REQUEST);
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image file", e);
        }
    }
}
