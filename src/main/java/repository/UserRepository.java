package repository;

import entity.users.Users;

public interface UserRepository<T extends Users> extends BaseEntityRepository<T,Integer>{

}
