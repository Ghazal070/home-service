package repository.impl;

import entity.users.Admin;
import jakarta.persistence.EntityManager;
import repository.AdminRepository;

public class AdminRepositoryImpl extends UserRepositoryImpl<Admin> implements AdminRepository {

    public AdminRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Admin> getEntityClass() {
        return Admin.class;
    }

    @Override
    public String getUniqueFieldName() {
        return null;
    }
}
