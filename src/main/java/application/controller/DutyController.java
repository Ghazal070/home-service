package application.controller;

import application.dto.UpdateDutyDto;
import application.dto.projection.UserLoginProjection;
import application.exception.ValidationControllerException;
import application.service.CustomerService;
import application.service.DutyService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/duties")
@RequiredArgsConstructor
public class DutyController {

    private final DutyService dutyService;


    @PatchMapping("/update")
    public ResponseEntity<String> updateDutyPriceOrDescription(@RequestBody UpdateDutyDto updateDutyDto){
        try{
            dutyService.updateDutyPriceOrDescription(updateDutyDto);
            return new ResponseEntity<>("Duty is updated",HttpStatus.OK);
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



}
