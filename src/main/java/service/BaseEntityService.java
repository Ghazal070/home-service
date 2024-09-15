package service;

import entity.BaseEntity;
import repository.impl.BaseEntityRepositoryImpl;

import java.io.Serializable;
import java.util.List;

public interface BaseEntityService<T extends BaseEntity<ID>,ID extends Serializable> {
    T save(T entity);
    T update (T newEntity);

    void delete (ID id);
    Boolean containById(ID id);
    T findById(ID id);
    List<T> loadAll();
    Boolean contain(T entity);
    T findByUniqId(String uniqId);
    Boolean deleteByUniqId(String uniqId);

}
