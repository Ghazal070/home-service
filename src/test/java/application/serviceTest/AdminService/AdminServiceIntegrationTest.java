package application.serviceTest.AdminService;

import application.dto.DutyCreation;
import application.dto.DutyResponseChildren;
import application.dto.UpdateDuty;
import application.entity.Duty;
import application.entity.enumeration.ExpertStatus;
import application.entity.users.Expert;
import application.repository.AdminRepository;
import application.service.AdminService;
import application.service.DutyService;
import application.service.ExpertService;
import application.service.PasswordEncodeService;
import application.util.AuthHolder;
import com.github.javafaker.Faker;
import jakarta.validation.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;;


@SpringBootTest
@Transactional
public class AdminServiceIntegrationTest {

    @Autowired
    private DutyService dutyService;
    @Autowired
    private ExpertService expertService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private AdminRepository repository;

    private Validator validator;
    @Autowired
    private AuthHolder authHolder;
    @Autowired
    private PasswordEncodeService passwordEncodeService;
    private Faker faker;

    @BeforeEach
    void setUp() {
        faker = new Faker();
    }

    @Test
    void adminTasks() throws IOException {
        String title = "Wash cloth";

        Duty duty = adminService.createDuty(
                DutyCreation.builder()
                        .title(title)
                        .parentId(298)
                        .basePrice(faker.number().numberBetween(100_000, 1_000_000))
                        .description(title + "---" + faker.lorem().characters(5, 20))
                        .selectable(true)
                        .build());
        Duty saveDuty = dutyService.save(duty);

        assertNotNull(duty);
        assertThat(saveDuty).hasFieldOrProperty("parent");
        assertThat(saveDuty.getTitle()).isEqualTo(title);
        assertThat(dutyService.existsById(saveDuty.getId())).isTrue();

        List<DutyResponseChildren> allDutyWithChildren = dutyService.loadAllDutyWithChildren();
        boolean match = allDutyWithChildren.stream().anyMatch(
                parent -> parent.getSubDuty().stream().anyMatch(
                        child -> child.getId().equals(saveDuty.getId()))
        );
        assertTrue(match);

        UpdateDuty updateDuty = UpdateDuty.builder()
                .dutyId(saveDuty.getId())
//                .price(1_000_000)
                .description("Wash ### cloth ****")
                .selectable(true)
                .build();
        Boolean updateDutyPriceOrDescription = dutyService.updateDutyPriceOrDescription(updateDuty);

        Integer saveDutyId = saveDuty.getId();
        assertTrue(updateDutyPriceOrDescription);
        assertThat(dutyService.findById(saveDutyId).get().getDescription())
                .contains("Wash cloth");
        assertThat(dutyService.findById(saveDutyId).get().getSelectable()).isTrue();


        Path imagePath = Path.of("src/test/resources/less300.jpg");
        byte[] imageData = Files.readAllBytes(imagePath);
        Byte[] imageDataWrapper = new Byte[imageData.length];
        for (int i = 0; i < imageData.length; i++) {
            imageDataWrapper[i] = imageData[i];
        }
        Expert expert = Expert.builder()
                .duties(new HashSet<>())
                .expertStatus(ExpertStatus.New)
                .image(imageDataWrapper)
                .build();
        Expert saveExpert = expertService.save(expert);

        assertThat(expertService.existsById(saveExpert.getId())).isNotNull();
        Boolean expertStatus = adminService.updateExpertStatus(saveExpert.getId());
        assertTrue(expertStatus);
        assertThat(expertService.findById(saveExpert.getId()).get().getExpertStatus()).isEqualTo(ExpertStatus.Accepted);




        Boolean addDutyToExpert = adminService.addDutyToExpert(saveExpert.getId(), saveDuty.getId());

        assertTrue(addDutyToExpert);
        assertThat(expertService.findById(saveExpert.getId()).get().getDuties().contains(saveDuty)).isTrue();

        Boolean removeDutyFromExpert = adminService.removeDutyFromExpert(saveExpert.getId(), saveDuty.getId());

        assertTrue(removeDutyFromExpert);
        assertThat(expertService.findById(saveExpert.getId()).get().getDuties().contains(saveDuty)).isFalse();
    }
}