package application.controller;

import application.dto.OfferCreationDto;
import application.dto.OfferResponseDto;
import application.entity.Offer;
import application.exception.ValidationControllerException;
import application.mapper.OfferMapper;
import application.service.ExpertService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/experts")
@RequiredArgsConstructor
public class ExpertController {

    private final ExpertService expertService;
    private final OfferMapper offerMapper;

    @GetMapping("/havePermission/{expertId}")
    public ResponseEntity<String> havePermissionExpertToServices(@PathVariable Integer expertId) {
        Boolean permissionExpertToServices = expertService.havePermissionExpertToServices(expertId);
        return permissionExpertToServices ? ResponseEntity.ok("Expert have permission") :
                new ResponseEntity<>("Expert dont have permission", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/offerCreation/{expertId}")
    public ResponseEntity<OfferResponseDto> sendOffer(@RequestBody OfferCreationDto offerCreationDto
            , @PathVariable Integer expertId) {
        try {
            Offer offer = expertService.sendOffer(offerCreationDto, expertId);
            return ResponseEntity.ok(offerMapper.convertEntityToDto(offer));
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
