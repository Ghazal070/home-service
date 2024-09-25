package application.repository.impl;

import application.entity.Comment;
import application.entity.Comment_;
import application.entity.Credit;
import application.entity.Credit_;
import application.repository.CommentRepository;
import application.repository.CreditRepository;
import jakarta.persistence.EntityManager;

public class CreditRepositoryImpl extends BaseEntityRepositoryImpl<Credit, Integer>
        implements CreditRepository {
    public CreditRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Credit> getEntityClass() {
        return Credit.class;
    }

    @Override
    public String getUniqueFieldName() {
        return Credit_.ID;
    }
}
