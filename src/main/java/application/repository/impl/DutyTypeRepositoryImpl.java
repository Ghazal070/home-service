package application.repository.impl;

import application.entity.DutyType;
import application.entity.DutyType_;
import jakarta.persistence.EntityManager;
import application.repository.DutyTypeRepository;

public class DutyTypeRepositoryImpl extends BaseEntityRepositoryImpl<DutyType, Integer>
        implements DutyTypeRepository {
    public DutyTypeRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<DutyType> getEntityClass() {
        return DutyType.class;
    }

    @Override
    public String getUniqueFieldName() {
        return DutyType_.TITLE;
    }
}
