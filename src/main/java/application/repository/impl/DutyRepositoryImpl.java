package application.repository.impl;

import application.dto.DutyResponseChildren;
import application.dto.UpdateDuty;
import application.entity.Duty;
import application.entity.Duty_;
import application.repository.DatabaseAccess;
import application.repository.DutyRepository;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

import java.util.*;

public class DutyRepositoryImpl extends BaseEntityRepositoryImpl<Duty, Integer>
        implements DutyRepository {

    public DutyRepositoryImpl(DatabaseAccess<Duty, Integer> databaseAccess) {
        super(databaseAccess);
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
        databaseAccess.beginTransaction();
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

        Query updateQuery = databaseAccess.updateQuery(queryBuilder.toString());

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
        databaseAccess.commitTransaction();
        return result > 0;
    }


    @Override
    public List<DutyResponseChildren> loadAllDutyWithChildren() {
        String query = """
                select d from Duty d order by case when d.parent.id is null then 1 else 2 end,d.parent.id
                """;
        TypedQuery<Duty> typedQuery = databaseAccess.createQuery(query);
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
    @Override
    public Boolean containByUniqField(String title, Integer parentId) {
        String query = """  
        select count(p) from %s p where p.title = :title and p.parent.id = :parentId  
    """.formatted(getEntityClass().getName());

        TypedQuery<Duty> typedQuery = databaseAccess.createQuery(query);
        typedQuery.setParameter("title", title);
        typedQuery.setParameter("parentId", parentId);
        List<Duty> duty = typedQuery.getResultList();
        if (duty!=null && !duty.isEmpty()){
            return true;
        }
        return false;
    }
}

