package application.controller;

import application.dto.OrderResponseDto;
import application.dto.OrderSubmissionDto;
import application.dto.UserChangePasswordDto;
import application.dto.projection.UserLoginProjection;
import application.entity.Order;
import application.entity.users.Customer;
import application.exception.ValidationControllerException;
import application.mapper.OrderMapper;
import application.service.CustomerService;
import application.service.ExpertService;
import application.service.PasswordEncodeService;
import application.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final CustomerService customerService;
    private final ExpertService expertService;


    @GetMapping("/customers/image/{userId}")
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

    @PostMapping("/experts/changePassword/{expertId}")
    public ResponseEntity<String> updatePasswordExpert (@RequestBody @Valid UserChangePasswordDto userChangePasswordDto,
                                               @PathVariable Integer expertId){
        try {
            Boolean updatePassword = expertService.updatePassword(userChangePasswordDto, expertId);
            return ResponseEntity.ok("Password Change");
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/customers/changePassword/{customerId}")
    public ResponseEntity<String> updatePasswordCustomer (@RequestBody @Valid UserChangePasswordDto userChangePasswordDto,
                                               @PathVariable Integer customerId){
        try {
            Boolean updatePassword = customerService.updatePassword(userChangePasswordDto, customerId);
            return ResponseEntity.ok("Password Change");
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
