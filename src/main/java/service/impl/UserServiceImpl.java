package service.impl;

import entity.users.Users;
import repository.UserRepository;
import service.UserService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class UserServiceImpl<U extends UserRepository<T>,T extends Users>
        extends BaseEntityServiceImpl<U,T,Integer> implements UserService<T> {


    public UserServiceImpl(U repository) {
        super(repository);
    }



    public void convertByteToImage(byte[] data, String firstNameId) {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BufferedImage bImage2 = null;
        try {
            bImage2 = ImageIO.read(bis);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        File file = new File("src/main/resources/images/" +firstNameId+ ".jpg");
        try {
            ImageIO.write(bImage2, "jpg",file);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
