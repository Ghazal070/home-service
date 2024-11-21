package application.controller;

import application.dto.*;
import application.entity.Offer;
import application.entity.Order;
import application.entity.users.Expert;
import application.exception.ValidationControllerException;
import application.mapper.OfferMapper;
import application.mapper.OrderExpertWaitingMapper;
import application.service.ExpertService;
import application.service.OrderService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/v1/experts")
@RequiredArgsConstructor
public class ExpertController {

    private final ExpertService expertService;
    private final OrderService orderService;
    private final OrderExpertWaitingMapper orderExpertWaitingMapper;
    private final OfferMapper offerMapper;

    @GetMapping("/permission/{expertId}")
    @PreAuthorize("hasAnyAuthority('expert-manage')")
    public ResponseEntity<String> havePermissionExpertToServices(@PathVariable Integer expertId) {
        Boolean permissionExpertToServices = expertService.havePermissionExpertToServices(expertId);
        return permissionExpertToServices ? ResponseEntity.ok("Expert have permission") :
                new ResponseEntity<>("Expert dont have permission", HttpStatus.BAD_REQUEST);
    }
    @PostMapping("/{expertId}/offers")
    @PreAuthorize("hasAnyAuthority('expert-manage')")
    public ResponseEntity<OfferResponseDto> sendOffer(@RequestBody OfferCreationDto offerCreationDto
            , @PathVariable Integer expertId) {
        try {
            Offer offer = expertService.sendOffer(offerCreationDto, expertId);
            return ResponseEntity.ok(offerMapper.convertEntityToDto(offer));
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{expertId}/scores")
    @PreAuthorize("hasAnyAuthority('expert-manage')")
    public ResponseEntity<List<ViewScoreExpertDto>> viewScores(@PathVariable Integer expertId) {
        try {
            List<ViewScoreExpertDto> viewScore = expertService.viewScores(expertId);
            return ResponseEntity.ok(viewScore);
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{expertId}/score")
    @PreAuthorize("hasAnyAuthority('expert-manage')")
    public ResponseEntity<String> viewScore(@PathVariable Integer expertId) {
        try {
            Integer integer = expertService.viewScore(expertId);
            return ResponseEntity.ok("Sum score is %s".formatted(integer));
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{expertId}/orders")
    @PreAuthorize("hasAnyAuthority('expert-manage')")
    public Set<OrderExpertWaitingDto> getOrdersForExpertWaitingOrChoosing(
            @PathVariable Integer expertId){
        try {
            Set<Order> orders = orderService.getOrdersForExpertWaitingOrChoosing(expertId);
            return orderExpertWaitingMapper.convertEntityToDto(orders);
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{expertId}/reports/offers")
    @PreAuthorize("hasAnyAuthority('expert-manage')")
    public ResponseEntity<Set<ReportExpertByOrderDTO>> expertReportOrders(
            @PathVariable Integer expertId){
        try {
            return ResponseEntity.ok(expertService.getExpertOffers(expertId));
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{expertId}/credit")
    @PreAuthorize("hasAnyAuthority('expert-manage')")
    public ResponseEntity<Integer> getCreditFindByCustomerId(
            @PathVariable Integer expertId){
        try {
            return ResponseEntity.ok(expertService.getCreditFindByExpertId(expertId));
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
