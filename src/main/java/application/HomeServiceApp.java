package application;

import application.dto.*;
import application.dto.projection.UserLoginProjection;
import application.entity.Duty;
import application.entity.Order;
import application.service.*;
import com.github.javafaker.Faker;
import application.entity.users.Users;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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
        OrderService orderService =context.getBean(OrderService.class,args);
        OfferService offerService =context.getBean(OfferService.class,args);

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
        //loadAllDuties(dutyService);
        //loadAllDutyWithChildrenTest(dutyService);
        //adminService.updateExpertStatus(293);
        //adminService.addDutyToExpert(293, 305);
        //adminService.removeDutyFromExpert(271,283);
        //dutyService.getSelectableDuties().forEach(System.out::println);
        //orderSubmitTest(faker, customerService);
        //orderService.getOrdersForExpertWaitingOrChoosing(293).forEach(System.out::println);
        //customerService.chooseExpertForOrder(325);
        //expertCreateOffer(expertService);
        //customerService.getOffersForOrder(324,315).forEach(System.out::println);
        //dutyService.getSelectableDuties().forEach(System.out::println);
        //orderService.getOrdersForExpertWaitingOrChoosing(327).forEach(System.out::println);




    }

    private static void expertCreateOffer(ExpertService expertService) {
        OfferCreationDto offerCreationDto = OfferCreationDto.builder()
                .orderId(324)
                .priceOffer(2000000)
                .dateTimeStartWork(LocalDateTime.now().plus(2, ChronoUnit.DAYS))
                .lengthDays(1)
                .build();
        expertService.sendOffer(offerCreationDto,327);
    }


    private static void orderSubmitTest(Faker faker, CustomerService customerService) {


        Order cleanHouse = customerService.orderSubmit(
                OrderSubmissionDto.builder()
                        .dutyId(299)
                        .priceOrder(800_000)
                        .dateTimeOrder(LocalDateTime.of(2024, 10, 30, 10, 25))
                        .address(faker.address().streetAddress())
                        .description( "8 --- " + faker.lorem().characters(5, 20))
                        .build()
        ,2);
    }

    private static void loadAllDutyWithChildrenTest(DutyService dutyService) {
        List<DutyResponseChildrenDto> duties = dutyService.loadAllDutyWithChildren();
        duties.forEach(System.out::println);
    }

    private static void loadAllDuties(DutyService dutyService) {
        List<Duty> duties = dutyService.findAll();
        duties.forEach(System.out::println);
    }

    private static void updatePriceOrDescriptionTest(DutyService dutyService) {
        UpdateDutyDto updateDutyDto = UpdateDutyDto.builder()
                .dutyId(305)
//                .price(1_000_000)
                .description("Sofa###Washing****")
                .selectable(true)
                .build();
        dutyService.updateDutyPriceOrDescription(updateDutyDto);
    }

    private static void adminCreateDutyDuplicate(Faker faker, AdminService adminService) {
        String sub = "cleanHouse";
        Integer parentId = 298;
        adminService.createDuty(
                DutyCreationDto.builder()
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
                DutyCreationDto.builder()
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
                    DutyCreationDto.builder()
                            .title(sub)
                            .parentId(297)
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
                    DutyCreationDto.builder()
                            .title(sub)
                            .parentId(298)
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
                    DutyCreationDto.builder()
                            .title(title)
                            .selectable(false)
                            .build()
            );
        }
    }

    private static void passwordUpdateTest(CustomerService customerService) {
        Integer userId=298;
        UserChangePasswordDto userChangePasswordDto = UserChangePasswordDto.builder()
                .oldPassword("wti94059")
                .newPassword("ghazal99").build();
        // Boolean aBoolean = expertService.updatePassword(userChangePassword);
        Boolean aBoolean = customerService.updatePassword(userChangePasswordDto,userId);
        System.out.println("change Password" + aBoolean);
    }

    private static void loginTestMethod(CustomerService customerService) {
        UserLoginProjection login = customerService.login("elisabeth.hills@gmail.com", "wti94059");
        //UserLoginProjection login = customerService.login("crystal.wunsch@gmail.com", "t4l29165");
    }

    private static void signupCustomerTestMethod(CustomerService customerService, SignupService signupService) {
        Faker faker = new Faker();
        UserSignupRequestDto userSignupRequestDto = createSignupRequest(faker, "Customer");
        Users signup = signupService.signup(userSignupRequestDto);
        customerService.convertByteToImage(signup.getId());
    }

    private static void signupExpertTestMethod(ExpertService expertService, SignupService signupService) {
        Faker faker = new Faker();
        UserSignupRequestDto userSignupRequestDto = createSignupRequest(faker, "Expert");
        Users signup = signupService.signup(userSignupRequestDto);
        expertService.convertByteToImage(signup.getId());
    }


    private static UserSignupRequestDto createSignupRequest(Faker faker, String role) {
        UserSignupRequestDto signupRequest;
        try {
            signupRequest = UserSignupRequestDto.builder()
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
