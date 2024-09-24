package application.repository.impl;

import application.entity.Duty;
import application.entity.Duty_;
import jakarta.persistence.EntityManager;
import application.repository.DutyRepository;
import jakarta.persistence.TypedQuery;

import java.util.List;

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

    @Override
    public Duty findByParentTitle(String title) {
        String query = "select d from Duty d where d.dutyType.title = :title";
        TypedQuery<Duty> typedQuery = entityManager.createQuery(query, Duty.class);
        typedQuery.setParameter("title",title);
        List<Duty> resultList = typedQuery.getResultList();

        return (resultList!=null && !resultList.isEmpty()) ? resultList.get(0):null;
    }
}

