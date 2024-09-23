package application;

import application.dto.DutyCreation;
import application.entity.Duty;
import application.entity.DutyType;
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

import java.util.ArrayList;
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
        DutyTypeRepository dutyTypeRepository = new DutyTypeRepositoryImpl(entityManager);
        DutyTypeService dutyTypeService = new DutyTypeServiceImpl(dutyTypeRepository);
        DutyRepository dutyRepository = new DutyRepositoryImpl(entityManager);
        DutyService dutyService = new DutyServiceImpl(dutyRepository);
        AdminRepository adminRepository = new AdminRepositoryImpl(entityManager);
        AdminService adminService = new AdminServiceImpl(adminRepository, authHolder, passwordEncode, dutyTypeService, dutyService);
        //       signupTestMethod(customerService, signupService);
        loginTestMethod(customerService);
        //passwordUpdateTset(customerService);
        List<DutyType> dutyTypeList = dutyTypeService.loadAll();
        //createDutyTypeFirstTime(adminService);
        if (dutyTypeList != null && !dutyTypeList.isEmpty()) {
            System.out.println("DutyType List: ");
            dutyTypeList.forEach(System.out::println);
        }

        createDutyFirstTime(adminService);


    }

    private static void createDutyFirstTime(AdminService adminService) {
        List<String> dutyTypes = List.of("Decoration", "BuildingFacilities", "CargoVehicles", "HouseholdAppliances", "Cleaning");
        for (String dutyType : dutyTypes) {
            adminService.createDuty(
                    DutyCreation.builder()
                            .titleDutyType(dutyType)
                            .build()
            );
        }
    }

    private static void createDutyTypeFirstTime(AdminService adminService) {
        List<String> dutyTypes = List.of("Decoration", "BuildingFacilities", "CargoVehicles", "HouseholdAppliances", "Cleaning");
        for (String dutyType : dutyTypes) {
            adminService.createDutyType(dutyType);

        }
    }

    private static void passwordUpdateTset(CustomerService customerService) {
        UserChangePassword userChangePassword = UserChangePassword.builder()
//                .oldPassword("lhsu7222")
                .oldPassword("ddd12345")
                .newPassword("ghazal99").build();
        // Boolean aBoolean = expertService.updatePassword(userChangePassword);
        Boolean aBoolean = customerService.updatePassword(userChangePassword);
        System.out.println("change Password" + aBoolean);
    }

    private static void loginTestMethod(CustomerService customerService) {
//        Users login = customerService.login("oscar.towne@gmail.com", "lhsu7222");
        Users login = customerService.login("sonja.hickle@yahoo.com", "ghazal99");
        //System.out.println(login);
    }

    private static void signupTestMethod(CustomerService customerService, SignupService signupService) {
        Faker faker = new Faker();
        String pathImage = "src/main/resources/images/less300.jpg";
        UserSignupRequest userSignupRequest0 = createSignupRequest(faker, "Expert", pathImage);
        UserSignupRequest userSignupRequest = createSignupRequest(faker, "Customer", pathImage);
        Users signup = signupService.signup(userSignupRequest);
        customerService.convertByteToImage(signup.getImage(), signup.getFirstName());
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
