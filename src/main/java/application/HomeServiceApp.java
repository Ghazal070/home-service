package application;

import application.dto.*;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

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
        CardService cardService = context.getBean(CardService.class,args);
        AdminReportByUser reportByUser = context.getBean(AdminReportByUser.class, args);
        OrderSpecification orderSpecification = context.getBean(OrderSpecification.class, args);


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
        //adminService.updateExpertStatus(352);
        //adminService.addDutyToExpert(352, 359);
        //adminService.removeDutyFromExpert(271,283);
        //dutyService.getSelectableDuties().forEach(System.out::println);
        //orderSubmitTest(faker, customerService);
        //orderService.getOrdersForExpertWaitingOrChoosing(293).forEach(System.out::println);
        //customerService.chooseExpertForOrder(367);
        //expertCreateOffer(expertService);
        //customerService.getOffersForOrder(324,315).forEach(System.out::println);
        //dutyService.getSelectableDuties().forEach(System.out::println);
        //List<UsersSearchResponse> responseList = searchAdminTest(adminService);
        //List<OrderReportDto> orders = reportByUser.getOrdersByUser(389, "Expert");
        //orders.forEach(System.out::println);
        adminOrderSearch(orderSpecification);


    }

    private static void adminOrderSearch(OrderSpecification orderSpecification) {
        SearchOrderDto searchOrderDto = SearchOrderDto.builder()
                .orderStatus("Done")
                .startDate(LocalDate.of(2020, 05, 06))
                .endDate(LocalDate.of(2025, 05, 06))
                .dutyId(Set.of(416,365))
                .build();
        orderSpecification.findAllBySearchOrderDto(searchOrderDto)
                .forEach(System.out::println);
    }

    private static List<UsersSearchResponse> searchAdminTest(AdminService adminService) {
        SearchDto searchDto = SearchDto.builder()
                .userRole("Expert")
                .firstName("r")
                .email("com")
                .dutyId(Set.of(416,358))
//                .maxScore(5)
                .build();
        return adminService.searchUser(searchDto);

    }

    private static void expertCreateOffer(ExpertService expertService) {
        OfferCreationDto offerCreationDto = OfferCreationDto.builder()
                .orderId(366)
                .priceOffer(2000000)
                .dateTimeStartWork(LocalDateTime.now().plus(2, ChronoUnit.DAYS))
                .lengthDays(1)
                .build();
        expertService.sendOffer(offerCreationDto,352);
    }


    private static void orderSubmitTest(Faker faker, CustomerService customerService) {


        Order cleanHouse = customerService.orderSubmit(
                OrderSubmissionDto.builder()
                        .dutyId(359)
                        .priceOrder(800_000)
                        .dateTimeOrderForDoingFromCustomer(LocalDateTime.of(2024, 10, 30, 10, 25))
                        .address(faker.address().streetAddress())
                        .description( "8 --- " + faker.lorem().characters(5, 20))
                        .build()
        ,350);
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
                            .parentId(356)
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
                            .parentId(357)
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
        Integer userId=350;
        UserChangePasswordDto userChangePasswordDto = UserChangePasswordDto.builder()
                .oldPassword("6v4m4668")
                .newPassword("ghazal99").build();
        // Boolean aBoolean = expertService.updatePassword(userChangePassword);
        Boolean aBoolean = customerService.updatePassword(userChangePasswordDto,userId);
        System.out.println("change Password  " + aBoolean);
    }

    private static void loginTestMethod(CustomerService customerService) {
        Boolean login = customerService.login("elisabeth.hills@gmail.com", "wti94059");
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
