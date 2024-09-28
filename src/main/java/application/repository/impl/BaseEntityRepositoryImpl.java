package application.repository.impl;

import application.entity.BaseEntity;
import application.repository.DatabaseAccess;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import application.repository.BaseEntityRepository;

import java.io.Serializable;
import java.util.List;

public abstract class BaseEntityRepositoryImpl<T extends BaseEntity<ID>, ID extends Serializable>
        implements BaseEntityRepository<T, ID> {
    protected final DatabaseAccess<T,ID> databaseAccess;

    public BaseEntityRepositoryImpl(DatabaseAccess<T,ID> databaseAccess) {
        this.databaseAccess = databaseAccess;
    }

    @Override
    public T save(T entity) {
        databaseAccess.beginTransaction();
        databaseAccess.persist(entity);
        databaseAccess.commitTransaction();
        return entity;
    }


    @Override
    public T update(T newEntity) {
        databaseAccess.beginTransaction();
        T mergeEntity = null;
        if (newEntity.getId() == null) throw new RuntimeException("NewEntity Cant Update...Id is null");
        else mergeEntity = databaseAccess.merge(newEntity);
        databaseAccess.commitTransaction();
        return mergeEntity;
    }


    @Override
    public void delete(ID id) {
        databaseAccess.beginTransaction();
        T entity = databaseAccess.find(getEntityClass(), id);
        if (entity != null) databaseAccess.remove(entity);
        else throw new RuntimeException("Error in removing application.entity: Entity with ID " + id + " is null.");
        databaseAccess.commitTransaction();
    }

    @Override
    public Boolean contain(T entity) {
        databaseAccess.beginTransaction();
        boolean contains = databaseAccess.contains(entity);
        databaseAccess.commitTransaction();
        return contains;
    }

    public T findById(ID id) {
        return databaseAccess.find(getEntityClass(), id);
    }

    public Boolean containById(ID id) {
        T entity = databaseAccess.find(getEntityClass(), id);
        return entity != null;
    }
    public Boolean containByUniqField(String uniqField) {
        String query = """
                select count(p) from %s p where p.%s= ?1
                """.formatted(getEntityClass().getName(), getUniqueFieldName());
        Long result = (Long) databaseAccess.createQuery(query)
                .setParameter(1, uniqField)
                .getSingleResult();
        return result>0;
    }


    @Override
    public List<T> loadAll() {
        List allRecord;
        String query = """
                from %s a
                """.formatted(getEntityClass().getName());
        allRecord = databaseAccess.createQuery(query).getResultList();
        return allRecord;
    }

    public T findByUniqId(String uniqId) {
        String query = """
                from %s p where p.%s= ?1
                """.formatted(getEntityClass().getName(), getUniqueFieldName());
        TypedQuery<T> typedQuery = databaseAccess.createQuery(query);
        List<T> resultList = typedQuery.setParameter(1, uniqId).getResultList();
        if (!resultList.isEmpty()) return resultList.get(0);
        return null;
    }

    @Override
    public Boolean deleteByUniqId(String uniqId) {
        databaseAccess.beginTransaction();
        String query = """
                from %s p where p.%s= ?1
                """.formatted(getEntityClass().getName(), getUniqueFieldName());
        TypedQuery<T> typedQuery = databaseAccess.createQuery(query);
        List<T> resultList = typedQuery.setParameter(1, uniqId).getResultList();
        if (!resultList.isEmpty()) {
            T entity = resultList.get(0);
            databaseAccess.remove(entity);
            databaseAccess.commitTransaction();
            return true;
        } else {
            databaseAccess.rollbackTransaction();
            return false;
        }
    }

    public abstract Class<T> getEntityClass();

    public abstract String getUniqueFieldName();
}

