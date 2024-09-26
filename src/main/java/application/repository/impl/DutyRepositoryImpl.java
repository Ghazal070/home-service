package application.repository.impl;

import application.dto.DutyResponseChildren;
import application.dto.UpdateDuty;
import application.entity.Duty;
import application.entity.Duty_;
import jakarta.persistence.EntityManager;
import application.repository.DutyRepository;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.*;

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
    public Boolean updateDutyPriceOrDescriptionOrSelectable(Duty duty, UpdateDuty updateDuty) {

        StringBuilder queryBuilder = new StringBuilder("update Duty d set ");
        entityManager.getTransaction().begin();
        boolean needsComma = false;
        if (updateDuty.getPrice() != null) {
            queryBuilder.append("d.basePrice = :price");
            needsComma = true;
        }
        if (updateDuty.getDescription() != null) {
            if (needsComma) {
                queryBuilder.append(", ");
            }
            queryBuilder.append("d.description = :description");
            needsComma = true;
        }
        if (updateDuty.getSelectable() != null) {
            if (needsComma) {
                queryBuilder.append(", ");
            }
            queryBuilder.append("d.selectable = :selectable");
        }
        queryBuilder.append(" where d.id = :id");

        Query updateQuery = entityManager.createQuery(queryBuilder.toString());

        if (updateDuty.getPrice() != null) {
            updateQuery.setParameter("price", updateDuty.getPrice());
        }
        if (updateDuty.getDescription() != null) {
            updateQuery.setParameter("description", updateDuty.getDescription());
        }
        if (updateDuty.getSelectable() != null) {
            updateQuery.setParameter("selectable", updateDuty.getSelectable());
        }
        updateQuery.setParameter("id", duty.getId());
        int result = updateQuery.executeUpdate();
        entityManager.getTransaction().commit();
        return result > 0;
    }


    @Override
    public List<DutyResponseChildren> loadAllDutyWithChildren() {
        String query = """
                select d from Duty d order by case when d.parent.id is null then 1 else 2 end,d.parent.id
                """;
        TypedQuery<Duty> typedQuery = entityManager.createQuery(query, Duty.class);
        List<Duty> duties = typedQuery.getResultList();
        Map<Integer, DutyResponseChildren> dutyResponseChildrenMap = new HashMap<>();
        List<DutyResponseChildren> rootDuties = new ArrayList<>();
        for (Duty duty : duties) {
            DutyResponseChildren responseChildren = DutyResponseChildren.builder()
                    .id(duty.getId())
                    .title(duty.getTitle())
                    .selectable(duty.getSelectable())
                    .subDuty(new HashSet<>())
                    .build();
            dutyResponseChildrenMap.put(duty.getId(), responseChildren);
            if (duty.getParent() == null) {
                rootDuties.add(responseChildren);
            } else {
                DutyResponseChildren parentResponse = dutyResponseChildrenMap.get(duty.getParent().getId());
                if (parentResponse != null) {
                    parentResponse.getSubDuty().add(responseChildren);
                }
            }
        }

        return rootDuties;
    }
}

