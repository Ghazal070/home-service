package service.impl;

import entity.users.User;
import repository.UserRepository;
import repository.impl.BaseEntityRepositoryImpl;
import service.UserService;

public class UserServiceImpl<U extends UserRepository<T>,T extends User>
        extends BaseEntityServiceImpl<U,T,Integer> implements UserService<T> {

    public UserServiceImpl(U repository) {
        super(repository);
    }

}
