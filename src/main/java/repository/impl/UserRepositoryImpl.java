package repository.impl;

import entity.users.User;
import jakarta.persistence.EntityManager;

public abstract class UserRepositoryImpl<T extends User> extends BaseEntityRepositoryImpl<T,Integer>{
    public UserRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }
}
