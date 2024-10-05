package application.service;

import application.dto.UserChangePasswordDto;
import application.dto.projection.UserLoginProjection;
import application.entity.users.Users;

public interface UserService<T extends Users> extends BaseEntityService<T,Integer> {

    void convertByteToImage(Byte[] data,String firstName);
    UserLoginProjection login(String email, String password);
    Boolean updatePassword(UserChangePasswordDto userChangePasswordDto);
    Boolean containByUniqField(String uniqField);

}
