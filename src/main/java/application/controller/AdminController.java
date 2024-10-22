package application.controller;

import application.constants.AuthorityNames;
import application.dto.*;
import application.exception.ValidationControllerException;
import application.service.AdminService;
import application.service.DutyService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/admins")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final DutyService dutyService;

    @PostMapping("/duties")
    @PreAuthorize("hasAuthority('admin-manage')")
    public ResponseEntity<DutyByIdParentIdDto> createDuty(@RequestBody @Valid DutyCreationDto dutyCreationDto) {
        try {
            adminService.createDuty(dutyCreationDto);
            //done cash duty Command Query Responsibility Segregation
            //todo cash dont return duty or use another method
            DutyByIdParentIdDto duty = dutyService.findByTitle(dutyCreationDto.getTitle());
            return new ResponseEntity<>(duty, HttpStatus.CREATED);
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PatchMapping("/management/experts/{expertId}/status/accept")
    @PreAuthorize("hasAuthority('admin-manage')")
    //done or management/experts/{expertId}?status=accept
    public ResponseEntity<String> updateExpertStatus(@PathVariable Integer expertId) {
        try {
            adminService.updateExpertStatus(expertId);
            return ResponseEntity.ok("Expert status change to Accepted");
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/experts/duties")
    @PreAuthorize("hasAuthority('admin-manage')")
    public ResponseEntity<String> addDutyToExpert(@RequestParam Integer expertId, @RequestParam Integer dutyId) {
        try {
            adminService.addDutyToExpert(expertId, dutyId);
            return ResponseEntity.ok("Add duty = %s to expert = %s ".formatted(expertId, dutyId));
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/experts/duties")
    @PreAuthorize("hasAuthority('admin-manage')")
    public ResponseEntity<String> removeDutyFromExpert(@RequestParam Integer expertId, @RequestParam Integer dutyId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("User: " + authentication.getName() + " Authorities: " + authentication.getAuthorities());
        try {
            adminService.removeDutyFromExpert(expertId, dutyId);
            return ResponseEntity.ok("Remove duty = %s to expert = %s ".formatted(expertId, dutyId));
        } catch (ValidationException exception) {
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/search")
    @PreAuthorize("hasAuthority('admin-manage')")
    public ResponseEntity<List<UsersSearchResponse>> adminSearchUser(@RequestBody @Valid SearchDto searchDto) {
        try {
            return ResponseEntity.ok(adminService.searchUser(searchDto));
        }catch (ValidationException exception){
            throw new ValidationControllerException(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}
