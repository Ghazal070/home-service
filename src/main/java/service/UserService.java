package service;

import entity.users.User;

public interface UserService<T extends User> extends BaseEntityService<T,Integer> {

}
