package application.controller;

import application.dto.OrderResponseDto;
import application.dto.OrderSubmissionDto;
import application.dto.projection.UserLoginProjection;
import application.entity.Order;
import application.entity.users.Customer;
import application.exception.ValidationControllerException;
import application.mapper.OrderMapper;
import application.service.CustomerService;
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


    @PostMapping("/login")
    public ResponseEntity<String> customerLogin (@RequestParam String username, @RequestParam String password){
        try {
            UserLoginProjection login = customerService.login(username, password);
            return ResponseEntity.ok("Customer is Authenticate");
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
