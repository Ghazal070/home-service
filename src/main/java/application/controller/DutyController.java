package application.controller;

import application.dto.DutyByIdTitleDto;
import application.dto.DutyResponseChildrenDto;
import application.dto.DutyResponseControllerDto;
import application.dto.UpdateDutyDto;
import application.entity.Duty;
import application.exception.ValidationControllerException;
import application.mapper.DutyControllerMapper;
import application.mapper.DutyControllerMapperFromDuty;
import application.mapper.DutyControllerMapperFromDutyById;
import application.service.DutyService;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/duties")
@RequiredArgsConstructor
public class DutyController {

    private final DutyService dutyService;
    private final DutyControllerMapper dutyControllerMapper;
    private final DutyControllerMapperFromDuty dutyControllerMapperFromDuty;
    private final DutyControllerMapperFromDutyById DutyControllerMapperFromDutyById;


    @PatchMapping("/update")
    @PreAuthorize("hasAuthority('admin-manage')")
    public ResponseEntity<String> updateDutyPriceOrDescription(@RequestBody UpdateDutyDto updateDutyDto) {
        try {
            dutyService.updateDutyPriceOrDescription(updateDutyDto);
            return new ResponseEntity<>("Duty is updated", HttpStatus.OK);
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/duties")
    @PreAuthorize("hasAnyAuthority('admin-manage','customer-manage','expert-manage')")
    public List<DutyResponseChildrenDto> loadAllDutyWithChildren() {
        try {
            return dutyService.loadAllDutyWithChildren();
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/duties/titles")
    @PreAuthorize("hasAnyAuthority('admin-manage','customer-manage','expert-manage')")
    public List<DutyResponseControllerDto> loadAllDutyWithChildrenOnlyTitle() {
        try {
            List<DutyResponseChildrenDto> list = dutyService.loadAllDutyWithChildren();
            return dutyControllerMapper
                    .convertEntityToDto(list);

        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/filter/uniq")
    @PreAuthorize("hasAuthority('admin-manage')")
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

    @GetMapping("/filter/exits")
    @PreAuthorize("hasAnyAuthority('admin-manage','customer-manage','expert-manage')")
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
    @GetMapping("/filter/selectable")
    @PreAuthorize("hasAnyAuthority('admin-manage','customer-manage','expert-manage')")
    public List<DutyByIdTitleDto> getSelectableDuties() {
        try {
            List<Duty> dutyList = dutyService.getSelectableDuties();
            return DutyControllerMapperFromDutyById.convertEntityToDto(dutyList);
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

}
