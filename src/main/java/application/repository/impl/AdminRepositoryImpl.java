package application.repository.impl;

import application.entity.users.Admin;

import application.entity.users.Profile_;
import application.repository.DatabaseAccess;
import jakarta.persistence.EntityManager;
import application.repository.AdminRepository;

public class AdminRepositoryImpl extends UserRepositoryImpl<Admin> implements AdminRepository {

    public AdminRepositoryImpl(DatabaseAccess<Admin, Integer> databaseAccess) {
        super(databaseAccess);
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
