package application.controller;

import application.dto.OfferResponseDto;
import application.entity.Offer;
import application.exception.ValidationControllerException;
import application.mapper.OfferMapper;
import application.service.OfferService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/offers")
@RequiredArgsConstructor
public class OfferController {
    private final OfferService offerService;
    private final OfferMapper offerMapper;

    @GetMapping("/orderByPrice")
    public Set<OfferResponseDto> getOfferByCustomerIdOrderByPriceOrder(
            @RequestParam Integer customerId,@RequestParam Integer orderID){
        try {
            Set<Offer> offers = offerService.getOfferByCustomerIdOrderByPriceOrder(customerId, orderID);
            return offerMapper.convertEntityToDto(offers);
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/orderByScoreExpert")
    public Set<OfferResponseDto> getOfferByCustomerIdOrderByScoreExpert(
            @RequestParam Integer customerId,@RequestParam Integer orderID){
        try {
            Set<Offer> offers = offerService.getOfferByCustomerIdOrderByScoreExpert(customerId, orderID);
            return offerMapper.convertEntityToDto(offers);
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
