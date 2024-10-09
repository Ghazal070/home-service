package application.controller;

import application.service.ExpertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/experts")
@RequiredArgsConstructor
public class ExpertController {

    private final ExpertService expertService;

//    @GetMapping("/havePermission/{expertId}")
//    public ResponseEntity<String> havePermissionExpertToServices(@PathVariable Integer expertId){
//
//        expertService.havePermissionExpertToServices(expertId);
//    }
}
