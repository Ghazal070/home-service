import com.github.javafaker.Faker;
import dto.UserChangePassword;
import dto.UserSignupRequest;
import entity.users.Expert;
import entity.users.Users;
import jakarta.persistence.EntityManager;
import repository.CustomerRepository;
import repository.ExpertRepository;
import repository.impl.CustomerRepositoryImpl;
import repository.impl.ExpertRepositoryImpl;
import service.CustomerService;
import service.ExpertService;
import service.PasswordEncode;
import service.SignupService;
import service.impl.CustomerServiceImpl;
import service.impl.ExpertServiceImpl;
import service.impl.PasswordEncodeImpl;
import service.impl.SignupServiceImpl;
import util.ApplicationContext;
import util.AuthHolder;

public class HomeServiceApp {

    public static void main(String[] args) {
//        ApplicationContext.getLogger().info("start of project");
        EntityManager entityManager = ApplicationContext.getINSTANCE().getEntityManager();
        CustomerRepository customerRepository = new CustomerRepositoryImpl(entityManager);
        ExpertRepository expertRepository = new ExpertRepositoryImpl(entityManager);
        AuthHolder authHolder = new AuthHolder();
        PasswordEncode passwordEncode = new PasswordEncodeImpl();
        CustomerService customerService = new CustomerServiceImpl(customerRepository, authHolder,passwordEncode);
        ExpertService expertService = new ExpertServiceImpl(expertRepository, authHolder,passwordEncode);
        SignupService signupService = new SignupServiceImpl(expertService, customerService, passwordEncode, authHolder);
        //signupTestMethod(customerService, signupService);
        loginTestMethod(customerService);
        UserChangePassword userChangePassword = UserChangePassword.builder()
                .oldPassword("lhsu7222")
                .newPassword("ghazal99").build();
        Boolean aBoolean = expertService.updatePassword(userChangePassword);
        System.out.println("change Password" + aBoolean);


    }

    private static void loginTestMethod(CustomerService customerService) {
        Users login = customerService.login("oscar.towne@gmail.com", "lhsu7222");
        System.out.println(login);
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
