package service;

import entity.users.Users;

public interface UserService<T extends Users> extends BaseEntityService<T,Integer> {

    void convertByteToImage(Byte[] data,String firstNameId);

}
