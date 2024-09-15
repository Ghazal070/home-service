package repository.impl;

import entity.BaseEntity;
import entity.Duty;
import entity.Duty_;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import repository.BaseEntityRepository;

import java.io.Serializable;
import java.util.List;

public class DutyRepositoryImpl extends BaseEntityRepositoryImpl<Duty,Integer>
        implements BaseEntityRepository<Duty,Integer> {

    public DutyRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Duty> getEntityClass() {
        return Duty.class;
    }

    @Override
    public String getUniqueFieldName() {
        return Duty_.ID;
    }
}

