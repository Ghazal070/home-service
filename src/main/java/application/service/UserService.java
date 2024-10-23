package application.service;

import application.dto.OrderReportDto;
import application.dto.SearchDto;
import application.dto.UserChangePasswordDto;
import application.dto.projection.UserLoginProjection;
import application.entity.users.Users;

import java.util.List;
import java.util.Optional;

public interface UserService<T extends Users> extends BaseEntityService<T,Integer> {

    void convertByteToImage(Integer userId);
    Boolean login(String email, String password);
    Boolean updatePassword(UserChangePasswordDto userChangePasswordDto,Integer userId);
    Boolean containByUniqField(String uniqField);
    Optional<T> findByEmail(String email);
}
