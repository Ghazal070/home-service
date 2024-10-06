package application.controller;

import application.dto.OrderResponseDto;
import application.dto.OrderSubmissionDto;
import application.entity.Order;
import application.exception.ValidationControllerException;
import application.mapper.OrderMapper;
import application.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final OrderMapper orderMapper;

    @PostMapping("/orderSubmit")
    public ResponseEntity<OrderResponseDto> orderSubmit(@RequestBody @Valid OrderSubmissionDto orderSubmissionDto){
        try{
            Order order = customerService.orderSubmit(orderSubmissionDto);
            OrderResponseDto orderResponseDto = orderMapper.convertEntityToDto(order);
            return ResponseEntity.ok(orderResponseDto);
        }catch (ValidationException e){
            throw new ValidationControllerException(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
