package service.impl;

import entity.users.Users;
import exception.ValidationException;
import repository.UserRepository;
import service.UserService;

import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
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



    public void convertByteToImage(Byte[] data, String firstNameId) {
        if(data == null || data.length==0)
            throw new ValidationException("image is null");
        byte [] dataByte = new byte[data.length];
        for (int i = 0; i < data.length; i++) {
            dataByte[i] = data[i];
        }
        BufferedImage bImage2;
        try(ByteArrayInputStream bis = new ByteArrayInputStream(dataByte)) {
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
