package application.service.impl;

import application.dto.UserChangePassword;
import application.entity.users.Users;
import application.exception.ValidationException;
import application.repository.UserRepository;
import application.service.PasswordEncode;
import application.service.UserService;
import application.util.AuthHolder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class UserServiceImpl<U extends UserRepository<T>, T extends Users>
        extends BaseEntityServiceImpl<U, T, Integer> implements UserService<T> {
    protected final AuthHolder authHolder;
    protected final PasswordEncode passwordEncode;

    public UserServiceImpl(U repository, AuthHolder authHolder, PasswordEncode passwordEncode) {
        super(repository);
        this.authHolder = authHolder;
        this.passwordEncode = passwordEncode;
    }


    public void convertByteToImage(Byte[] data, String firstNameId) {
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
        File file = new File("src/main/resources/images/" + firstNameId + ".jpg");
        try {
            ImageIO.write(bImage2, "jpg", file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Users login(String email, String password) {
        if (!email.isBlank() && !password.isBlank()) {
            Users loginUser = repository.login(email, password);
            authHolder.tokenId = loginUser.getId();
            authHolder.tokenName = loginUser.getProfile().getEmail();
            return loginUser;
        }
        return null;
    }

    @Override
    public Boolean updatePassword(UserChangePassword userChangePassword) {
        if (userChangePassword!=null){
            String oldPassword = userChangePassword.getOldPassword();
            String newPassword = userChangePassword.getNewPassword();
            T user = findById(authHolder.tokenId);
            if (user!=null){
                if((user.getProfile().getPassword()).equals(oldPassword)){
                    String encode = passwordEncode.encode(newPassword);
                    Boolean repoResponse = repository.updatePassword(authHolder.tokenName,encode);
                    if (repoResponse){
                        return true;
                    }else throw new ValidationException("repo response is false");

                }else throw new ValidationException("old password is not correct");
            }else throw new ValidationException("user from token is null");
        }
        return null;
    }
}
