package application.repository.impl;

import application.entity.Duty;
import application.entity.Duty_;
import jakarta.persistence.EntityManager;
import application.repository.DutyRepository;

public class DutyRepositoryImpl extends BaseEntityRepositoryImpl<Duty,Integer>
        implements DutyRepository{

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

