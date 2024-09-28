package application.repository.impl;

import application.repository.DatabaseAccess;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.hibernate.query.Query;

import java.io.Serializable;

public class JpaDatabaseAccess<T, ID extends Serializable> implements DatabaseAccess<T,ID> {
    private final EntityManager entityManager;

    public JpaDatabaseAccess(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public T find(Class<T> entityClass, ID id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public void persist(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public T merge(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void remove(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public TypedQuery<T> createQuery(String queryString) {
        return entityManager.createQuery(queryString, (Class<T>) Object.class);
    }

    @Override
    public void beginTransaction() {
        entityManager.getTransaction().begin();
    }

    @Override
    public void commitTransaction() {
        entityManager.getTransaction().commit();
    }

    @Override
    public void rollbackTransaction() {
        entityManager.getTransaction().rollback();
    }

    @Override
    public Boolean contains(T entity) {
        return entityManager.contains(entity);
    }

    @Override
    public Query updateQuery(String queryString) {
        return (Query) entityManager.createQuery(queryString);
    }
}