package application.controller;

import application.dto.UserChangePasswordDto;
import application.dto.UserSignupRequestDto;
import application.dto.projection.UserLoginProjection;
import application.entity.users.Users;
import application.exception.ValidationControllerException;
import application.service.*;
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
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final CustomerService customerService;
    private final ExpertService expertService;
    private final SignupService signupService;
    private final ObjectMapper objectMapper;


    @GetMapping("/customers/{userId}/image")
    public ResponseEntity<String> convertByteToImage(@PathVariable Integer userId) {
        try{
            customerService.convertByteToImage(userId);
            return ResponseEntity.ok("Convert done");
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/customers/login")
    public ResponseEntity<String> customerLogin (@RequestParam String username, @RequestParam String password){
        try {
            UserLoginProjection login = customerService.login(username, password);
            return ResponseEntity.ok("Customer is Authenticate");
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/experts/login")
    public ResponseEntity<String> expertLogin (@RequestParam String username, @RequestParam String password){
        try {
            UserLoginProjection login = expertService.login(username, password);
            return ResponseEntity.ok("Expert is Authenticate");
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/experts/{expertId}/password")
    public ResponseEntity<String> updatePasswordExpert (@RequestBody @Valid UserChangePasswordDto userChangePasswordDto,
                                               @PathVariable Integer expertId){
        try {
            Boolean updatePassword = expertService.updatePassword(userChangePasswordDto, expertId);
            return ResponseEntity.ok("Password Change");
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/customers/{customerId}/password")
    public ResponseEntity<String> updatePasswordCustomer (@RequestBody @Valid UserChangePasswordDto userChangePasswordDto,
                                               @PathVariable Integer customerId){
        try {
            Boolean updatePassword = customerService.updatePassword(userChangePasswordDto, customerId);
            return ResponseEntity.ok("Password Change");
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(value = "/signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> signup(
            @RequestPart("user") @Valid String userJson,
            @RequestPart("image") MultipartFile imageFile) {
        try {
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


//done sum score expert