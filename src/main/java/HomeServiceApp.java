import com.github.javafaker.Faker;
import dto.UserSignupRequest;
import entity.Duty;
import entity.enumeration.Role;
import entity.users.User;
import entity.users.factory.CustomerFactory;
import entity.users.factory.ExpertFactory;
import entity.users.factory.UserFactory;
import exception.ValidationException;
import jakarta.persistence.EntityManager;
import lombok.SneakyThrows;
import repository.UserRepository;
import util.ApplicationContext;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class HomeServiceApp {

    public static void main(String[] args) {
//        ApplicationContext.getLogger().info("start of project");
        EntityManager entityManager = ApplicationContext.getINSTANCE().getEntityManager();

        Faker faker = new Faker();
        String pathImage = "src/main/resources/images/Tools_clipart.png";
        UserSignupRequest userSignupRequest = createSignupRequest(faker, Role.Expert, pathImage);
        UserFactory userFactory = switch (userSignupRequest.getRole()){
            case Customer -> new CustomerFactory();
            case Expert -> new ExpertFactory();
            default -> throw new ValidationException("role must be Customer or Expert");
        };
        User user = userFactory.createUser(userSignupRequest);
        System.out.println(user);



    }





    private static UserSignupRequest createSignupRequest(Faker faker, Role role, String pathImage) {
        UserSignupRequest signupRequest = UserSignupRequest.builder()
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .email(faker.internet().emailAddress())
                .password(faker.name().username())
                .pathImage(pathImage)
                .role(role)
                .build();
        return signupRequest;
    }
}
