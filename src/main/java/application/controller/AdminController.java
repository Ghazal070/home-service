package application.controller;

import application.dto.DutyCreationDto;
import application.dto.DutyResponseDto;
import application.entity.Duty;
import application.exception.ValidationControllerException;
import application.service.AdminService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/dutyCreation")
    public ResponseEntity<DutyResponseDto> createDuty(@RequestBody @Valid DutyCreationDto dutyCreationDto){
        try{
            Duty duty = adminService.createDuty(dutyCreationDto);
            DutyResponseDto dutyResponseDto = DutyResponseDto.builder()
                    .id(duty.getId())
                    .title(duty.getTitle())
                    .basePrice(duty.getBasePrice())
                    .parent(duty.getParent() !=null ?
                            DutyResponseDto.builder().id(duty.getParent().getId())
                                    .title(duty.getParent().getTitle())
                                    .basePrice(duty.getBasePrice())
                                    .build():null)
                    .selectable(dutyCreationDto.getSelectable())
                    .build();

            return new ResponseEntity<>(dutyResponseDto, HttpStatus.CREATED);
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/expert/{id}/status")
    public ResponseEntity updateExpertStatus(@PathVariable Integer id){
        try {
        adminService.updateExpertStatus(id);
        return ResponseEntity.ok("Expert status change to Accepted");
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }



}
