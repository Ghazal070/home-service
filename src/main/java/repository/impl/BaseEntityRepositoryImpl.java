package repository.impl;

import entity.BaseEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.yaml.snakeyaml.events.Event;
import repository.BaseEntityRepository;

import java.io.Serializable;
import java.util.List;

public abstract class BaseEntityRepositoryImpl<T extends BaseEntity<ID>, ID extends Serializable>
        implements BaseEntityRepository<T, ID> {
    protected final EntityManager entityManager;

    public BaseEntityRepositoryImpl(EntityManager entityManager) {

        this.entityManager = entityManager;
    }

    @Override
    public T save(T entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        return entity;
    }


    @Override
    public T update(T newEntity) {
        entityManager.getTransaction().begin();
        T mergeEntity = null;
        if (newEntity.getId() == null) throw new RuntimeException("NewEntity Cant Update...Id is null");
        else mergeEntity = entityManager.merge(newEntity);
        entityManager.getTransaction().commit();
        return mergeEntity;
    }


    @Override
    public void delete(ID id) {
        entityManager.getTransaction().begin();
        T entity = entityManager.find(getEntityClass(), id);
        if (entity != null) entityManager.remove(entity);
        else throw new RuntimeException("Error in removing entity: Entity with ID " + id + " is null.");
        entityManager.getTransaction().commit();
    }

    @Override
    public Boolean contain(T entity) {
        entityManager.getTransaction().begin();
        boolean contains = entityManager.contains(entity);
        entityManager.getTransaction().commit();
        return contains;
    }

    public T findById(ID id) {
        return entityManager.find(getEntityClass(), id);
    }

    public Boolean containById(ID id) {
        T entity = entityManager.find(getEntityClass(), id);
        return entity != null;
    }

    @Override
    public List<T> loadAll() {
        List allRecord;
        String query = """
                from %s a
                """.formatted(getEntityClass().getName());
        allRecord = entityManager.createQuery(query).getResultList();
        return allRecord;
    }

    public T findByUniqId(String uniqId) {
        String query = """
                from %s p where p.%s= ?1
                """.formatted(getEntityClass().getName(), getUniqueFieldName());
        TypedQuery<T> typedQuery = entityManager.createQuery(query, getEntityClass());
        List<T> resultList = typedQuery.setParameter(1, uniqId).getResultList();
        if (!resultList.isEmpty()) return resultList.get(0);
        return null;
    }

    @Override
    public Boolean deleteByUniqId(String uniqId) {
        entityManager.getTransaction().begin();
        String query = """
                from %s p where p.%s= ?1
                """.formatted(getEntityClass().getName(), getUniqueFieldName());
        TypedQuery<T> typedQuery = entityManager.createQuery(query, getEntityClass());
        List<T> resultList = typedQuery.setParameter(1, uniqId).getResultList();
        if (!resultList.isEmpty()) {
            T entity = resultList.get(0);
            entityManager.remove(entity);
            entityManager.getTransaction().commit();
            return true;
        } else {
            entityManager.getTransaction().rollback();
            return false;
        }
    }

    public abstract Class<T> getEntityClass();

    public abstract String getUniqueFieldName();
}

