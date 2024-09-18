import com.github.javafaker.Faker;
import dto.UserSignupRequest;
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

public class HomeServiceApp {

    public static void main(String[] args) {
//        ApplicationContext.getLogger().info("start of project");
        EntityManager entityManager = ApplicationContext.getINSTANCE().getEntityManager();
        Faker faker = new Faker();
        String pathImage = "src/main/resources/images/Tools_clipart.jpg";
        //UserSignupRequest userSignupRequest = createSignupRequest(faker, "Expert", pathImage);
        UserSignupRequest userSignupRequest = createSignupRequest(faker, "Customer", pathImage);
        CustomerRepository customerRepository = new CustomerRepositoryImpl(entityManager);
        ExpertRepository expertRepository = new ExpertRepositoryImpl(entityManager);
        CustomerService customerService = new CustomerServiceImpl(customerRepository);
        ExpertService expertService = new ExpertServiceImpl(expertRepository);
        PasswordEncode passwordEncode = new PasswordEncodeImpl();
        SignupService signupService = new SignupServiceImpl(expertService, customerService, passwordEncode);
        Users signup = signupService.signup(userSignupRequest);
        customerService.convertByteToImage(signup.getImage(),signup.getFirstName());


    }


    private static UserSignupRequest createSignupRequest(Faker faker, String role, String pathImage) {
        UserSignupRequest signupRequest = UserSignupRequest.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password(faker.lorem().characters(4)+faker.number().numberBetween(1000,9999))
                .pathImage(pathImage)
                .role(role)
                .build();
        return signupRequest;
    }
}
