package application;

import application.dto.*;
import application.dto.projection.UserLoginProjection;
import application.entity.Duty;
import application.entity.Order;
import application.entity.users.userFactory.CustomerFactory;
import application.entity.users.userFactory.ExpertFactory;
import application.repository.*;
import application.service.*;
import application.service.impl.*;
import com.github.javafaker.Faker;
import application.entity.users.Users;
import jakarta.persistence.EntityManager;
import application.util.AuthHolder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class HomeServiceApp {

    public static void main(String[] args) {
        Faker faker = new Faker();
        ConfigurableApplicationContext context = SpringApplication.run(HomeServiceApp.class, args);
        AdminService adminService = context.getBean(AdminService.class, args);
        CustomerService customerService = context.getBean(CustomerService.class, args);
        SignupService signupService = context.getBean(SignupService.class, args);
        ExpertService expertService = context.getBean(ExpertService.class, args);
        DutyService dutyService = context.getBean(DutyService.class, args);

        //signupCustomerTestMethod(customerService, signupService);
        //signupExpertTestMethod(expertService, signupService);
        //loginTestMethod(customerService);
        //passwordUpdateTest(customerService);
        //adminCreateDutyFirstTime(adminService);
        //adminCreateHouseholdAppliances(faker, adminService);
        //adminCreateCleaning(faker, adminService);
        //adminCreateDutyDuplicate(faker,adminService);
        //adminCreateDutyDontExitParentDuty(faker, adminService);
        //updatePriceOrDescriptionTest(dutyService);
        loadAllDuties(dutyService);
        //loadAllDutyWithChildrenTest(dutyService);
        //adminService.updateExpertStatus(2);
        //adminService.addDutyToExpert(2, 8);
        //adminService.removeDutyFromExpert(2,8);
        //dutyService.getSelectableDuties().forEach(System.out::println);
        //orderSubmitTest(faker, customerService);


    }


    private static void orderSubmitTest(Faker faker, CustomerService customerService) {


        Order cleanHouse = customerService.orderSubmit(
                OrderSubmission.builder()
                        .id(8)
                        .priceOrder(700_000)
                        .dateTimeOrder(LocalDateTime.of(2024, 10, 30, 10, 25))
                        .address(faker.address().streetAddress())
                        .description( "8 --- " + faker.lorem().characters(5, 20))
                        .build()
        );
    }

    private static void loadAllDutyWithChildrenTest(DutyService dutyService) {
        List<DutyResponseChildren> duties = dutyService.loadAllDutyWithChildren();
        duties.forEach(System.out::println);
    }

    private static void loadAllDuties(DutyService dutyService) {
        List<Duty> duties = dutyService.findAll();
        duties.forEach(System.out::println);
    }

    private static void updatePriceOrDescriptionTest(DutyService dutyService) {
        UpdateDuty updateDuty = UpdateDuty.builder()
                .dutyId(14)
//                .price(1_000_000)
                .description("Sofa###Washing****")
                .selectable(true)
                .build();
        dutyService.updateDutyPriceOrDescription(updateDuty);
    }

    private static void adminCreateDutyDuplicate(Faker faker, AdminService adminService) {
        String sub = "cleanHouse";
        Integer parentId = 7;
        adminService.createDuty(
                DutyCreation.builder()
                        .title(sub)
                        .parentId(parentId)
                        .basePrice(faker.number().numberBetween(100_000, 1_000_000))
                        .description(sub + "---" + faker.lorem().characters(5, 20))
                        .selectable(true)
                        .build()
        );

    }

    private static void adminCreateDutyDontExitParentDuty(Faker faker, AdminService adminService) {
        String sub = "WashMachine";
        Integer parentId = 250;
        adminService.createDuty(
                DutyCreation.builder()
                        .title(sub)
                        .parentId(parentId)
                        .basePrice(faker.number().numberBetween(100_000, 1_000_000))
                        .description(sub + "---" + faker.lorem().characters(5, 20))
                        .selectable(true)
                        .build()
        );

    }

    private static void adminCreateHouseholdAppliances(Faker faker, AdminService adminService) {
        List<String> subDutyHouseholdAppliancesList = List.of(
                "KitchenAppliances", "LaundrySupplies", "AudioVideoEquipment"
        );
        for (String sub : subDutyHouseholdAppliancesList) {
            adminService.createDuty(
                    DutyCreation.builder()
                            .title(sub)
                            .parentId(6)
                            .basePrice(faker.number().numberBetween(100_000, 1_000_000))
                            .description(sub + "---" + faker.lorem().characters(5, 20))
                            .selectable(true)
                            .build()
            );

        }
    }

    private static void adminCreateCleaning(Faker faker, AdminService adminService) {
        List<String> subDutyHouseholdAppliancesList = List.of(
                "cleanHouse", "LaundryIroning", "CarpetCleaning", "SofaWashing", "Spraying"
        );
        for (String sub : subDutyHouseholdAppliancesList) {
            adminService.createDuty(
                    DutyCreation.builder()
                            .title(sub)
                            .parentId(7)
                            .basePrice(faker.number().numberBetween(100_000, 1_000_000))
                            .description(sub + "---" + faker.lorem().characters(5, 20))
                            .selectable(true)
                            .build()
            );

        }
    }


    private static void adminCreateDutyFirstTime(AdminService adminService) {
        List<String> dutyTypes = List.of("Decoration", "BuildingFacilities", "CargoVehicles", "HouseholdAppliances", "Cleaning");
        for (String title : dutyTypes) {
            adminService.createDuty(
                    DutyCreation.builder()
                            .title(title)
                            .selectable(false)
                            .build()
            );
        }
    }

    private static void passwordUpdateTest(CustomerService customerService) {
        UserChangePassword userChangePassword = UserChangePassword.builder()
                .oldPassword("t4l29165")
                .newPassword("ghazal99").build();
        // Boolean aBoolean = expertService.updatePassword(userChangePassword);
        Boolean aBoolean = customerService.updatePassword(userChangePassword);
        System.out.println("change Password" + aBoolean);
    }

    private static void loginTestMethod(CustomerService customerService) {
        //UserLoginProjection login = customerService.login("dalia.buckridge@gmail.com", "tb8y6430");
        UserLoginProjection login = customerService.login("crystal.wunsch@gmail.com", "t4l29165");
    }

    private static void signupCustomerTestMethod(CustomerService customerService, SignupService signupService) {
        Faker faker = new Faker();
        UserSignupRequest userSignupRequest = createSignupRequest(faker, "Customer");
        Users signup = signupService.signup(userSignupRequest);
        customerService.convertByteToImage(signup.getImage(), signup.getFirstName());
    }

    private static void signupExpertTestMethod(ExpertService expertService, SignupService signupService) {
        Faker faker = new Faker();
        UserSignupRequest userSignupRequest = createSignupRequest(faker, "Expert");
        Users signup = signupService.signup(userSignupRequest);
        expertService.convertByteToImage(signup.getImage(), signup.getFirstName());
    }


    private static UserSignupRequest createSignupRequest(Faker faker, String role) {
        UserSignupRequest signupRequest;
        try {
            signupRequest = UserSignupRequest.builder()
                    .firstName(faker.name().firstName())
                    .lastName(faker.name().lastName())
                    .email(faker.internet().emailAddress())
                    .password(faker.lorem().characters(4) + faker.number().numberBetween(1000, 9999))
                    .inputStream(new FileInputStream("src/main/resources/images/less300.jpg"))
                    .role(role)
                    .build();
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Error in File Input Stream in main");
        }
        return signupRequest;
    }
}
