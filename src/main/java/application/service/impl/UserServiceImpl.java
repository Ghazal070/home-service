package application.service.impl;

import application.dto.UserChangePassword;
import application.dto.projection.UserLoginProjection;
import application.entity.users.Users;
import application.exception.ValidationException;
import application.repository.UserRepository;
import application.service.PasswordEncode;
import application.service.UserService;
import application.util.AuthHolder;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Optional;


public class UserServiceImpl<U extends UserRepository<T>, T extends Users>
        extends BaseEntityServiceImpl<U, T, Integer> implements UserService<T> {
    protected final AuthHolder authHolder;
    protected final PasswordEncode passwordEncode;

    public UserServiceImpl(Validator validator, U repository, AuthHolder authHolder, PasswordEncode passwordEncode) {
        super(validator, repository);
        this.authHolder = authHolder;
        this.passwordEncode = passwordEncode;
    }

    public void convertByteToImage(Byte[] data, String firstName) {
        if (data == null || data.length == 0)
            throw new ValidationException("image is null");
        byte[] dataByte = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            dataByte[i] = data[i];
        }
        BufferedImage bImage2;
        try (ByteArrayInputStream bis = new ByteArrayInputStream(dataByte)) {
            bImage2 = ImageIO.read(bis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File file = new File("src/main/resources/images/" + firstName + ".jpg");
        try {
            ImageIO.write(bImage2, "jpg", file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserLoginProjection login(String email, String password) {
        if (!email.isBlank() && !password.isBlank()) {
            Optional<UserLoginProjection> loginUser = repository.login(email, password);
            if (loginUser.isPresent()) {
                authHolder.tokenId = loginUser.get().getId();
                authHolder.tokenName = loginUser.get().getProfile().getEmail();
                return loginUser.get();
            }
            throw new ValidationException("Invalid login credentials");
        } else
            throw new ValidationException("Email or Password must not be empty");

    }

    @Override
    public Boolean updatePassword(UserChangePassword userChangePassword) {
        if (userChangePassword != null) {
            String oldPassword = userChangePassword.getOldPassword();
            String newPassword = userChangePassword.getNewPassword();
            Optional<T> user = findById(authHolder.getTokenId());
            if (user.isPresent()) {
                String encodePassword =oldPassword;
                if (passwordEncode.isEqualEncodeDecodePass(oldPassword, encodePassword)) {
                    String encode = passwordEncode.encode(newPassword);
                    int repoResponse = repository.updatePassword(authHolder.getTokenName(), encode);
                    return repoResponse > 0;
                } else throw new ValidationException("old password is not correct");
            } else throw new ValidationException("user from token is null");
        }
        throw new ValidationException("UserChangePassword is null");
    }

    @Override
    public Boolean containByUniqField(String uniqField) {
        return repository.containByUniqField(uniqField);
    }

}
