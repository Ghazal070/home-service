package application.controller;

import application.dto.UserChangePasswordDto;
import application.dto.UserLoginDto;
import application.dto.UserSignupRequestDto;
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
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('customer-manage')")
    public ResponseEntity<String> convertByteToImage(@PathVariable Integer userId) {
        try {
            customerService.convertByteToImage(userId);
            return ResponseEntity.ok("Convert done");
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/customers/login")
//    @PreAuthorize("hasAuthority('customer-manage')")
    @PreAuthorize("permitAll()")
    public ResponseEntity<String> customerLogin(@RequestBody @Valid UserLoginDto userLoginDto) {
        try {
            customerService.login(userLoginDto.getEmail(), userLoginDto.getPassword());
            return ResponseEntity.ok("Customer is Authenticate");
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/experts/login")
    @PreAuthorize("hasAuthority('expert-manage')")
    public ResponseEntity<String> expertLogin(@RequestParam String username, @RequestParam String password) {
        try {
            expertService.login(username, password);
            return ResponseEntity.ok("Expert is Authenticate");
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/experts/{expertId}/password")
    @PreAuthorize("hasAuthority('expert-manage')")
    public ResponseEntity<String> updatePasswordExpert(@RequestBody @Valid UserChangePasswordDto userChangePasswordDto,
                                                       @PathVariable Integer expertId) {
        try {
            expertService.updatePassword(userChangePasswordDto, expertId);
            return ResponseEntity.ok("Password Change");
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/customers/{customerId}/password")
    @PreAuthorize("hasAuthority('customer-manage')")
    public ResponseEntity<String> updatePasswordCustomer(@RequestBody @Valid UserChangePasswordDto userChangePasswordDto,
                                                         @PathVariable Integer customerId) {
        try {
            customerService.updatePassword(userChangePasswordDto, customerId);
            return ResponseEntity.ok("Password Change");
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
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

    @GetMapping("/customers/activate")
    public ResponseEntity<String> activateCustomerAccount(@RequestParam String token) {
        try {
            Boolean tokenValidation = customerService.validateVerificationToken(token);
            if (tokenValidation) {
                return ResponseEntity.ok("Your account has been activated.");
            } else {
                return ResponseEntity.badRequest().body("Account not activated.");
            }
        } catch (ValidationException exception) {

            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/experts/activate")
    public ResponseEntity<String> activateExpertAccount(@RequestParam String token) {
        try {
            Boolean tokenValidation = expertService.validateVerificationToken(token);
            if (tokenValidation) {
                return ResponseEntity.ok("Your account has been activated. Please wait for admin Accepted.");
            } else {
                return ResponseEntity.badRequest().body("Account not activated.");
            }
        } catch (ValidationException exception) {

            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}

