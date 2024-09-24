package application.repository.impl;

import application.dto.UpdateDuty;
import application.entity.Duty;
import application.entity.Duty_;
import jakarta.persistence.EntityManager;
import application.repository.DutyRepository;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class DutyRepositoryImpl extends BaseEntityRepositoryImpl<Duty, Integer>
        implements DutyRepository {

    public DutyRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Duty> getEntityClass() {
        return Duty.class;
    }

    @Override
    public String getUniqueFieldName() {
        return Duty_.TITLE;
    }

    @Override
    public Boolean updateDutyPriceOrDescription(Duty duty, UpdateDuty updateDuty) {
        entityManager.getTransaction().begin();
        Query updateQuery = null;
        StringBuilder sb = new StringBuilder("update Duty d set ");
        if (updateDuty.getPrice() != null && updateDuty.getDescription() == null) {
            sb.append("d.basePrice=:price where d.id=:id");
            updateQuery = entityManager.createQuery(String.valueOf(sb));
            updateQuery.setParameter("price", updateDuty.getPrice());
        } else if (updateDuty.getPrice() == null && updateDuty.getDescription() != null) {
            sb.append("d.description= :description  where d.id=:id");
            updateQuery = entityManager.createQuery(String.valueOf(sb));
            updateQuery.setParameter("description", updateDuty.getDescription());
        } else if (updateDuty.getDescription() != null && updateDuty.getPrice() != null) {
            sb.append("d.description= :description, d.basePrice = :price where d.id= :id");
            updateQuery = entityManager.createQuery(String.valueOf(sb));
            updateQuery.setParameter("description", updateDuty.getDescription());
            updateQuery.setParameter("price", updateDuty.getPrice());
        }
        updateQuery.setParameter("id", duty.getId());
        int result = updateQuery.executeUpdate();
        entityManager.getTransaction().commit();
        return result > 0;
    }

    public Boolean updateDutyPriceOrDescriptionRefactor(Duty duty, UpdateDuty updateDuty) {
        Boolean result = false;
        entityManager.getTransaction().begin();
        StringBuilder sb = new StringBuilder("update Duty d set ");
        boolean firstPart = true;
        if (updateDuty.getPrice() != null) {
            sb.append("d.basePrice = :price");
            firstPart = false;
        }
        if (updateDuty.getDescription() != null) {
            if (!firstPart) {
                sb.append(", ");
            }
            sb.append("d.description = :description");
        }
        sb.append(" where d.id = :id");
        Query updateQuery = entityManager.createQuery(sb.toString());
        if (updateDuty.getPrice() != null) {
            updateQuery.setParameter("price", updateDuty.getPrice());
        }
        if (updateDuty.getDescription() != null) {
            updateQuery.setParameter("description", updateDuty.getDescription());
        }
        updateQuery.setParameter("id", duty.getId());
        int updateCount = updateQuery.executeUpdate();
        entityManager.getTransaction().commit();
        return updateCount > 0;
    }
}

