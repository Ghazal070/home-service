package application.repository.impl;

import application.entity.Comment;
import application.entity.Comment_;
import application.entity.Credit;
import application.entity.Credit_;
import application.repository.CommentRepository;
import application.repository.CreditRepository;
import application.repository.DatabaseAccess;
import jakarta.persistence.EntityManager;

public class CreditRepositoryImpl extends BaseEntityRepositoryImpl<Credit, Integer>
        implements CreditRepository {
    public CreditRepositoryImpl(DatabaseAccess<Credit, Integer> databaseAccess) {
        super(databaseAccess);
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
