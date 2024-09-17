//package repository.userRepositoryFactory;
//
//import entity.users.Users;
//import repository.UserRepository;
//import service.UserService;
//import service.impl.BaseEntityServiceImpl;
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
//    public UserServiceImplBaseOnUserFactoryRepo(U repository, UserRepositoryFactory userRepositoryFactory) {
//        super(repository);
//        this.userRepositoryFactory = userRepositoryFactory;
//    }
//
//    @Override
//    public T save(T entity) {
//        UserRepository<? extends Users> repository = userRepositoryFactory.getRepository(entity);
//        return repository.save(entity);
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
////i have two error in this code 1-in line 25 entity capture of...
////2- in cunstructor i think i must dont have repo
////i think i dont extends baseEntity