package application.service;

import application.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface BaseEntityService<T extends BaseEntity<ID>,ID extends Serializable> {
    T save(T entity);
    T update (T newEntity);

    void deleteById(ID id);
    Boolean existsById(ID id);
    Optional<T> findById(ID id);
    List<T> findAll();
    Boolean exists(T entity);



}
