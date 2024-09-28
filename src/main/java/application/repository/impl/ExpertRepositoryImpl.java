package application.repository.impl;

import application.entity.users.Expert;
import application.entity.users.Profile_;
import application.repository.DatabaseAccess;
import jakarta.persistence.EntityManager;
import application.repository.ExpertRepository;

public class ExpertRepositoryImpl extends UserRepositoryImpl<Expert> implements ExpertRepository {

    public ExpertRepositoryImpl(DatabaseAccess<Expert, Integer> databaseAccess) {
        super(databaseAccess);
    }

    @Override
    public Class<Expert> getEntityClass() {
        return Expert.class;
    }

    @Override
    public String getUniqueFieldName() {
        return Profile_.EMAIL;
    }
}
