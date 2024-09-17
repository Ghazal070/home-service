package repository.impl;

import entity.users.Users;
import jakarta.persistence.EntityManager;
import repository.UserRepository;

public abstract class UserRepositoryImpl<T extends Users> extends BaseEntityRepositoryImpl<T,Integer> implements UserRepository<T> {
    public UserRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

}
