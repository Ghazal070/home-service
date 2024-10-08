package application.controller;

import application.dto.projection.UserLoginProjection;
import application.exception.ValidationControllerException;
import application.service.CustomerService;
import application.service.DutyService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/duties")
@RequiredArgsConstructor
public class DutyController {

    private final DutyService dutyService;


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
