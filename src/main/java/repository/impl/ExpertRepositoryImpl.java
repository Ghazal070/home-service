package repository.impl;

import entity.users.Expert;
import entity.users.User;
import jakarta.persistence.EntityManager;
import repository.ExpertRepository;

public class ExpertRepositoryImpl extends UserRepositoryImpl<Expert> implements ExpertRepository {

    public ExpertRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Expert> getEntityClass() {
        return Expert.class;
    }

    @Override
    public String getUniqueFieldName() {
        return null;
    }
}
