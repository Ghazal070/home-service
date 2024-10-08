package application.service.impl;

import application.dto.UserSignupRequestDto;
import application.entity.users.Customer;
import application.entity.users.Expert;
import application.entity.users.Users;
import application.entity.users.userFactory.CustomerFactory;
import application.entity.users.userFactory.ExpertFactory;
import jakarta.validation.ValidationException;
import application.service.CustomerService;
import application.service.ExpertService;
import application.service.PasswordEncodeService;
import application.service.SignupService;
import application.util.AuthHolder;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Set;

@Service
public class SignupServiceImpl implements SignupService {

    private final ExpertService expertService;
    private final CustomerService customerService;
    private final PasswordEncodeService passwordEncodeService;
    private final AuthHolder authHolder;
    private final ExpertFactory expertFactory;
    private final CustomerFactory customerFactory;
    private final Validator validator;

    public SignupServiceImpl(ExpertService expertService, CustomerService customerService, PasswordEncodeService passwordEncodeService, AuthHolder authHolder, ExpertFactory expertFactory, CustomerFactory customerFactory, Validator validator) {
        this.expertService = expertService;
        this.customerService = customerService;
        this.passwordEncodeService = passwordEncodeService;
        this.authHolder = authHolder;
        this.expertFactory = expertFactory;
        this.customerFactory = customerFactory;
        this.validator = validator;
    }
//todo prototype design pattern for input stream
    @Override
    public Users signup(UserSignupRequestDto userSignupRequestDto) {
        InputStream originalInputStream = userSignupRequestDto.getInputStream();
        byte[] imageBytes = copyInputStream(originalInputStream);
        userSignupRequestDto.setInputStream(new ByteArrayInputStream(imageBytes));
        InputStream inputStreamCopy = new ByteArrayInputStream(imageBytes);
        Set<ConstraintViolation<UserSignupRequestDto>> violations = validator.validate(userSignupRequestDto);
        if (!isImageJpg(inputStreamCopy)) {
            throw new ValidationException("Image must be in JPG format");
        }
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder("Validation failed: ");
            for (ConstraintViolation<UserSignupRequestDto> violation : violations) {
                sb.append(violation.getMessage()).append(" ");
            }
            throw new ValidationException(sb.toString().trim());
        }

        switch (userSignupRequestDto.getRole()) {
            case "Expert" -> {
                if (expertService.containByUniqField(userSignupRequestDto.getEmail())) {
                    throw new ValidationException("Email must be unique");
                }
                userSignupRequestDto.setPassword(
                        passwordEncodeService.encode(userSignupRequestDto.getPassword())
                );
                Expert expert = (Expert) expertFactory.createUser(userSignupRequestDto);
                expert = expertService.save(expert);
                authHolder.tokenName = expert.getProfile().getEmail();
                authHolder.tokenId = expert.getId();
                return expert;
            }
            case "Customer" -> {
                if (customerService.containByUniqField(userSignupRequestDto.getEmail())) {
                    throw new ValidationException("Email must be unique");
                }
                userSignupRequestDto.setPassword(
                        passwordEncodeService.encode(userSignupRequestDto.getPassword())
                );
                Customer customer = (Customer) customerFactory.createUser(userSignupRequestDto);
                customer = customerService.save(customer);
                authHolder.tokenId = customer.getId();
                authHolder.tokenName = customer.getProfile().getEmail();
                return customer;
            }
            default -> throw new IllegalArgumentException("Only Expert or Customer can sign up");
        }
    }

    public boolean isImageJpg(InputStream inputStream) {
        try {
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(inputStream);
            Iterator<ImageReader> readers = ImageIO.getImageReaders(imageInputStream);

            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                String formatName = reader.getFormatName();
//                inputStream.reset();
                return "JPEG".equalsIgnoreCase(formatName) || "jpg".equalsIgnoreCase(formatName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private byte[] copyInputStream(InputStream inputStream) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to copy InputStream", e);
        }

    }
}
