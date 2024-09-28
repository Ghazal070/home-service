package application.repository;

import jakarta.persistence.TypedQuery;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.List;

public interface DatabaseAccess<T, ID extends Serializable> {
    T find(Class<T> entityClass, ID id);
    void persist(T entity);
    T merge(T entity);
    void remove(T entity);
    TypedQuery<T> createQuery(String queryString);
    Query updateQuery(String queryString);
    void beginTransaction();
    void commitTransaction();
    void rollbackTransaction();

    Boolean contains(T entity);

}
