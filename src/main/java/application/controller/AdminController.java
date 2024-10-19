package application.controller;

import application.dto.DutyCreationDto;
import application.dto.DutyResponseDto;
import application.dto.SearchDto;
import application.dto.UsersSearchResponse;
import application.entity.Duty;
import application.exception.ValidationControllerException;
import application.service.AdminService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/dutyCreation")
    public ResponseEntity<DutyResponseDto> createDuty(@RequestBody @Valid DutyCreationDto dutyCreationDto) {
        try {
            Duty duty = adminService.createDuty(dutyCreationDto);
            //todo cash duty Command Query Responsibility Segregation
            //todo cash dont return duty
            DutyResponseDto dutyResponseDto = DutyResponseDto.builder()
                    .id(duty.getId())
                    .title(duty.getTitle())
                    .basePrice(duty.getBasePrice())
                    .parent(duty.getParent() != null ?
                            DutyResponseDto.builder().id(duty.getParent().getId())
                                    .title(duty.getParent().getTitle())
                                    .basePrice(duty.getBasePrice())
                                    .build() : null)//todo recursive for parent
                    //todo return id seprate for create and get duty method call service
                    .selectable(dutyCreationDto.getSelectable())
                    .build();

            return new ResponseEntity<>(dutyResponseDto, HttpStatus.CREATED);
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
//todo duties
    //todo
    @PatchMapping("/expertStatus/{expertId}")
    //todo {expertId} Accepted سجاد فرحانی‌زاده
    //7:44 PM
    ///management/experts/{expertId}/status/accept
    //
    //سجاد فرحانی‌زاده
    //7:44 PM
    ///management/experts/{expertId}?status=accept
    public ResponseEntity<String> updateExpertStatus(@PathVariable Integer expertId) {
        try {
            adminService.updateExpertStatus(expertId);
            return ResponseEntity.ok("Expert status change to Accepted");
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/addDutyToExpert")
    public ResponseEntity<String> addDutyToExpert(@RequestParam Integer expertId, @RequestParam Integer dutyId) {
        try {
            adminService.addDutyToExpert(expertId, dutyId);
            return ResponseEntity.ok("Add duty = %s to expert = %s ".formatted(expertId, dutyId));
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/removeDutyFromExpert")
    public ResponseEntity<String> removeDutyFromExpert(@RequestParam Integer expertId, @RequestParam Integer dutyId) {
        try {
            adminService.removeDutyFromExpert(expertId, dutyId);
            return ResponseEntity.ok("Remove duty = %s to expert = %s ".formatted(expertId, dutyId));
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/search")
    public ResponseEntity<List<UsersSearchResponse>> adminSearchUser(@RequestBody @Valid SearchDto searchDto) {
        try {
            return ResponseEntity.ok(adminService.searchUser(searchDto));
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
