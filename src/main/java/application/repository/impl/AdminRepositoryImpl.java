package application.repository.impl;

import application.entity.users.Admin;

import application.entity.users.Profile_;
import jakarta.persistence.EntityManager;
import application.repository.AdminRepository;

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
        return Profile_.EMAIL;
    }
}
