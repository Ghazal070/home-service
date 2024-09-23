//package application.repository.userRepositoryFactory;
//
//import application.entity.users.Users;
//import application.repository.UserRepository;
//import application.service.UserService;
//import application.service.impl.BaseEntityServiceImpl;
//
//import javax.imageio.ImageIO;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayInputStream;
//import java.io.File;
//import java.io.IOException;
//
//public class UserServiceImplBaseOnUserFactoryRepo <U extends UserRepository<T>,T extends Users>
//        extends BaseEntityServiceImpl<U,T,Integer> implements UserService<T>  {
//    private final UserRepositoryFactory userRepositoryFactory;
//    public UserServiceImplBaseOnUserFactoryRepo(U application.repository, UserRepositoryFactory userRepositoryFactory) {
//        super(application.repository);
//        this.userRepositoryFactory = userRepositoryFactory;
//    }
//
//    @Override
//    public T save(T application.entity) {
//        UserRepository<? extends Users> application.repository = userRepositoryFactory.getRepository(application.entity);
//        return application.repository.save(application.entity);
//    }
//
//    public void convertByteToImage(byte[] data, String firstNameId) {
//        ByteArrayInputStream bis = new ByteArrayInputStream(data);
//        BufferedImage bImage2 = null;
//        try {
//            bImage2 = ImageIO.read(bis);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        File file = new File("src/main/resources/images/" +firstNameId+ ".png");
//        try {
//            ImageIO.write(bImage2, "png",file);
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//}
////i have two error in this code 1-in line 25 application.entity capture of...
////2- in cunstructor i think i must dont have repo
////i think i dont extends baseEntity