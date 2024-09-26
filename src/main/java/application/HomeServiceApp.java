package application;

import application.dto.DutyCreation;
import application.dto.UpdateDuty;
import application.entity.Duty;
import application.entity.enumeration.ExpertStatus;
import application.repository.*;
import application.repository.impl.*;
import application.service.*;
import application.service.impl.*;
import com.github.javafaker.Faker;
import application.dto.UserChangePassword;
import application.dto.UserSignupRequest;
import application.entity.users.Users;
import jakarta.persistence.EntityManager;
import application.util.ApplicationContext;
import application.util.AuthHolder;

import java.util.List;

public class HomeServiceApp {

    public static void main(String[] args) {
//        ApplicationContext.getLogger().info("start of project");
        Faker faker = new Faker();
        EntityManager entityManager = ApplicationContext.getINSTANCE().getEntityManager();
        CustomerRepository customerRepository = new CustomerRepositoryImpl(entityManager);
        ExpertRepository expertRepository = new ExpertRepositoryImpl(entityManager);
        AuthHolder authHolder = new AuthHolder();
        PasswordEncode passwordEncode = new PasswordEncodeImpl();
        CustomerService customerService = new CustomerServiceImpl(customerRepository, authHolder, passwordEncode);
        ExpertService expertService = new ExpertServiceImpl(expertRepository, authHolder, passwordEncode);
        SignupService signupService = new SignupServiceImpl(expertService, customerService, passwordEncode, authHolder);
        DutyRepository dutyRepository = new DutyRepositoryImpl(entityManager);
        DutyService dutyService = new DutyServiceImpl(dutyRepository);
        AdminRepository adminRepository = new AdminRepositoryImpl(entityManager);
        AdminService adminService = new AdminServiceImpl(adminRepository, authHolder, passwordEncode,dutyService, expertService);
        signupCustomerTestMethod(customerService, signupService);
        //signupExpertTestMethod(expertService, signupService);
        //loginTestMethod(customerService);
        //passwordUpdateTest(customerService);
        //adminCreateDutyFirstTime(adminService);
        //adminCreateHouseholdAppliances(faker, adminService);
        //adminCreateCleaning(faker, adminService);
        //adminCreateDutyDontExitParentDuty(faker, adminService);
        //adminCreateDutyDuplicate(faker,adminService);
        //updatePriceOrDescriptionTest(dutyService);
        //loadAllDuties(dutyService);
        //loadAllDutyWithChildrenTest(dutyService);
        //adminService.updateExpertStatus(expertService.findById(171), ExpertStatus.Accepted);
        //adminService.addDutyToExpert(expertService.findById(170), dutyService.findById(159));
        //adminService.removeDutyFromExpert(expertService.findById(170), dutyService.findById(159));


    }

    private static void loadAllDutyWithChildrenTest(DutyService dutyService) {
        List<Duty> duties = dutyService.loadAllDutyWithChildren();
        duties.forEach(System.out::println);
    }

    private static void loadAllDuties(DutyService dutyService) {
        List<Duty> duties = dutyService.loadAll();
        duties.forEach(System.out::println);
    }

    private static void updatePriceOrDescriptionTest(DutyService dutyService) {
        UpdateDuty updateDuty = UpdateDuty.builder()
                .title("SofaWashing")
                .price(600_000)
                .description("SofaWashing**")
                .build();
        dutyService.updateDutyPriceOrDescription(updateDuty);
    }
    private static void adminCreateDutyDuplicate(Faker faker, AdminService adminService) {
        String sub = "cleanHouse";
        String parentTitle = "Cleaning";
        adminService.createDuty(
                DutyCreation.builder()
                        .title(sub)
                        .parentTitle(parentTitle)
                        .basePrice(faker.number().numberBetween(100_000, 1_000_000))
                        .description(sub + "---" + faker.lorem().characters(5, 20))
                        .build()
        );

    }

    private static void adminCreateDutyDontExitParentDuty(Faker faker, AdminService adminService) {
        String sub = "WashMachine";
        String parentTitle = "Washing";
        adminService.createDuty(
                DutyCreation.builder()
                        .title(sub)
                        .parentTitle(parentTitle)
                        .basePrice(faker.number().numberBetween(100_000, 1_000_000))
                        .description(sub + "---" + faker.lorem().characters(5, 20))
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
                            .parentTitle("HouseholdAppliances")
                            .basePrice(faker.number().numberBetween(100_000, 1_000_000))
                            .description(sub + "---" + faker.lorem().characters(5, 20))
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
                            .parentTitle("Cleaning")
                            .basePrice(faker.number().numberBetween(100_000, 1_000_000))
                            .description(sub + "---" + faker.lorem().characters(5, 20))
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
                            .build()
            );
        }
    }

    private static void passwordUpdateTest(CustomerService customerService) {
        UserChangePassword userChangePassword = UserChangePassword.builder()
//                .oldPassword("lhsu7222")
                .oldPassword("lhsu7222")
                .newPassword("ghazal99").build();
        // Boolean aBoolean = expertService.updatePassword(userChangePassword);
        Boolean aBoolean = customerService.updatePassword(userChangePassword);
        System.out.println("change Password" + aBoolean);
    }

    private static void loginTestMethod(CustomerService customerService) {
        Users login = customerService.login("oscar.towne@gmail.com", "lhsu7222");
        //Users login = customerService.login("sonja.hickle@yahoo.com", "ghazal99");
    }

    private static void signupCustomerTestMethod(CustomerService customerService, SignupService signupService) {
        Faker faker = new Faker();
        String pathImage = "src/main/resources/images/less300.jpg";
        UserSignupRequest userSignupRequest = createSignupRequest(faker, "Customer", pathImage);
        Users signup = signupService.signup(userSignupRequest);
        customerService.convertByteToImage(signup.getImage(), signup.getFirstName());
    }
    private static void signupExpertTestMethod(ExpertService expertService, SignupService signupService) {
        Faker faker = new Faker();
        String pathImage = "src/main/resources/images/less300.jpg";
        UserSignupRequest userSignupRequest = createSignupRequest(faker, "Expert", pathImage);
        Users signup = signupService.signup(userSignupRequest);
        expertService.convertByteToImage(signup.getImage(), signup.getFirstName());
    }


    private static UserSignupRequest createSignupRequest(Faker faker, String role, String pathImage) {
        UserSignupRequest signupRequest = UserSignupRequest.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password(faker.lorem().characters(4) + faker.number().numberBetween(1000, 9999))
                .pathImage(pathImage)
                .role(role)
                .build();
        return signupRequest;
    }
}
