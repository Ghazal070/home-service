package application.repository;

import application.entity.users.Users;

public interface UserRepository<T extends Users> extends BaseEntityRepository<T,Integer>{

    Users login(String email, String password);
    Boolean updatePassword(String email,String newPassword);
}
