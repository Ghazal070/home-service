package application.controller;

import application.dto.OrderExpertWaitingDto;
import application.dto.OrderResponseDto;
import application.entity.Order;
import application.exception.ValidationControllerException;
import application.mapper.OrderExpertWaitingMapper;
import application.mapper.OrderMapper;
import application.service.OrderService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final OrderExpertWaitingMapper orderExpertWaitingMapper;

    @GetMapping("/expertWaiting/{expertId}")
    public Set<OrderExpertWaitingDto> getOrdersForExpertWaitingOrChoosing(
            @PathVariable Integer expertId){
        try {
            Set<Order> orders = orderService.getOrdersForExpertWaitingOrChoosing(expertId);
            return orderExpertWaitingMapper.convertEntityToDto(orders);
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
