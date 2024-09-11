package repository;

import entity.users.User;

public interface UserRepository<T extends User> extends BaseEntityRepository<T,Integer>{

}
