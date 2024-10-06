package application.controller;

import application.dto.OrderSubmissionDto;
import application.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

//    @PostMapping("/orderSubmit")
//    public ResponseEntity<OrderResponseDto> orderSubmit(OrderSubmissionDto orderSubmissionDto){
//
//    }
}
