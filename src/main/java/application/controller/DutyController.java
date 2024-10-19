package application.controller;

import application.dto.DutyResponseChildrenDto;
import application.dto.DutyResponseControllerDto;
import application.dto.UpdateDutyDto;
import application.entity.Duty;
import application.exception.ValidationControllerException;
import application.mapper.DutyControllerMapper;
import application.mapper.DutyControllerMapperFromDuty;
import application.service.DutyService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/duties")
@RequiredArgsConstructor
public class DutyController {

    private final DutyService dutyService;
    private final DutyControllerMapper dutyControllerMapper;
    private final DutyControllerMapperFromDuty dutyControllerMapperFromDuty;


    @PatchMapping("/update")
    public ResponseEntity<String> updateDutyPriceOrDescription(@RequestBody UpdateDutyDto updateDutyDto) {
        try {
            dutyService.updateDutyPriceOrDescription(updateDutyDto);
            return new ResponseEntity<>("Duty is updated", HttpStatus.OK);
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/dutiesWithSubDuties")
    public List<DutyResponseChildrenDto> loadAllDutyWithChildren() {
        try {
            return dutyService.loadAllDutyWithChildren();
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/dutiesWithSubDutiesOnlyTitle")
//todo    @GetMapping(params="only=title")
    public List<DutyResponseControllerDto> loadAllDutyWithChildrenOnlyTitle() {
        try {
            List<DutyResponseChildrenDto> list = dutyService.loadAllDutyWithChildren();
            return dutyControllerMapper
                    .convertEntityToDto(list);

        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/uniqField")
    public ResponseEntity<String> containByUniqField(
            @RequestParam String title, @RequestParam Integer parentId) {
        Boolean contain = dutyService.containByUniqField(title, parentId);
        return contain ? new ResponseEntity<>(
                "%s exit with parentId %d".formatted(title, parentId)
                , HttpStatus.BAD_REQUEST
        ) : ResponseEntity.ok(
                "%s is not exit with parentId %d".formatted(title, parentId)
        );
    }

    @GetMapping("/exitTitle")
    public ResponseEntity<String> existsByTitle(
            @RequestParam String title) {
        Boolean contain = dutyService.existsByTitle(title);
        return contain ? new ResponseEntity<>(
                "%s exit".formatted(title)
                , HttpStatus.BAD_REQUEST
        ) : ResponseEntity.ok(
                "%s is not exit".formatted(title)
        );
    }

    @ResponseBody
    @GetMapping("/allSelectableDuties")
    public List<DutyResponseControllerDto> getSelectableDuties() {
        try {
            List<Duty> dutyList = dutyService.getSelectableDuties();
            return dutyControllerMapperFromDuty.convertEntityToDto(dutyList);
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
