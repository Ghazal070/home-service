package service;

import dto.UserChangePassword;
import entity.users.Users;

public interface UserService<T extends Users> extends BaseEntityService<T,Integer> {

    void convertByteToImage(Byte[] data,String firstNameId);
    Users login(String email, String password);
    Boolean updatePassword(UserChangePassword userChangePassword);

}
