package application.service.impl;

import application.dto.UserChangePasswordDto;
import application.dto.projection.UserLoginProjection;
import application.entity.users.Users;
import jakarta.validation.ValidationException;
import application.repository.UserRepository;
import application.service.PasswordEncodeService;
import application.service.UserService;
import application.util.AuthHolder;
import jakarta.validation.Validator;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Optional;


public class UserServiceImpl<U extends UserRepository<T>, T extends Users>
        extends BaseEntityServiceImpl<U, T, Integer> implements UserService<T> {
    protected final AuthHolder authHolder;
    protected final PasswordEncodeService passwordEncodeService;

    public UserServiceImpl(Validator validator, U repository, AuthHolder authHolder, PasswordEncodeService passwordEncodeService) {
        super(validator, repository);
        this.authHolder = authHolder;
        this.passwordEncodeService = passwordEncodeService;
    }

    public void convertByteToImage(Integer userId) {
        Optional<T> users = repository.findById(userId);
        if (users.isPresent()){
            Byte[] data = users.get().getImage();
            String firstName = users.get().getFirstName();
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
        }else throw new ValidationException("UserId is not exist");

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
    public Boolean updatePassword(UserChangePasswordDto userChangePasswordDto) {
        if (userChangePasswordDto != null) {
            String oldPassword = userChangePasswordDto.getOldPassword();
            String newPassword = userChangePasswordDto.getNewPassword();
            Optional<T> user = findById(authHolder.getTokenId());
            if (user.isPresent()) {
                String encodePassword =oldPassword;
                if (passwordEncodeService.isEqualEncodeDecodePass(oldPassword, encodePassword)) {
                    String encode = passwordEncodeService.encode(newPassword);
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
