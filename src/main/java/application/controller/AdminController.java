package application.controller;

import application.dto.DutyCreationDto;
import application.dto.DutyResponseDto;
import application.entity.Duty;
import application.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;


@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/dutyCreation")
    public ResponseEntity<DutyResponseDto> createDuty(@RequestBody @Valid DutyCreationDto dutyCreationDto){
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
    }



}
