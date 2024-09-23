package application.repository;

import application.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;

public interface BaseEntityRepository <T extends BaseEntity<ID>, ID extends Serializable> {

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
