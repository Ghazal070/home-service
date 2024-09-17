package service;

import entity.users.Users;

public interface UserService<T extends Users> extends BaseEntityService<T,Integer> {

    void convertByteToImage(byte[] data,String firstNameId);

}
