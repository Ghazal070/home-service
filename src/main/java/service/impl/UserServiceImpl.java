package service.impl;

import entity.users.User;
import lombok.SneakyThrows;
import repository.UserRepository;
import repository.impl.BaseEntityRepositoryImpl;
import service.UserService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserServiceImpl<U extends UserRepository<T>,T extends User>
        extends BaseEntityServiceImpl<U,T,Integer> implements UserService<T> {

    public UserServiceImpl(U repository) {
        super(repository);
    }

    public void convertByteToImage(byte[] data,String firstNameId) {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BufferedImage bImage2 = null;
        try {
            bImage2 = ImageIO.read(bis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File file = new File("src/main/resources/images/" +firstNameId+ ".png");
        try {
            ImageIO.write(bImage2, "png",file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
