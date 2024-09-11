package repository.impl;

import entity.users.Expert;
import entity.users.User;
import jakarta.persistence.EntityManager;

public class ExpertRepositoryImpl extends UserRepositoryImpl<Expert> {

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
