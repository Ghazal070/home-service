package repository.impl;

import entity.users.User;
import jakarta.persistence.EntityManager;
import repository.UserRepository;

public abstract class UserRepositoryImpl<T extends User> extends BaseEntityRepositoryImpl<T,Integer> implements UserRepository<T> {
    public UserRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
