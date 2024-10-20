package application.controller;

import application.dto.OfferCreationDto;
import application.dto.OfferResponseDto;
import application.dto.ViewScoreExpertDto;
import application.entity.Offer;
import application.entity.users.Expert;
import application.exception.ValidationControllerException;
import application.mapper.OfferMapper;
import application.service.ExpertService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/experts")
@RequiredArgsConstructor
public class ExpertController {

    private final ExpertService expertService;
    private final OfferMapper offerMapper;

    @GetMapping("/permission/{expertId}")
    public ResponseEntity<String> havePermissionExpertToServices(@PathVariable Integer expertId) {
        Boolean permissionExpertToServices = expertService.havePermissionExpertToServices(expertId);
        return permissionExpertToServices ? ResponseEntity.ok("Expert have permission") :
                new ResponseEntity<>("Expert dont have permission", HttpStatus.BAD_REQUEST);
    }
//done isActive true handle it in service //negative score??
    @PostMapping("/{expertId}/offers")
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
    public ResponseEntity<List<ViewScoreExpertDto>> viewScores(@PathVariable Integer expertId) {
        try {
            List<ViewScoreExpertDto> viewScore = expertService.viewScores(expertId);
            return ResponseEntity.ok(viewScore);
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{expertId}/score")
    public ResponseEntity<String> viewScore(@PathVariable Integer expertId) {
        try {
            Integer integer = expertService.viewScore(expertId);
            return ResponseEntity.ok("Sum score is %s".formatted(integer));
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
