package application.service.impl;

import application.entity.BaseEntity;
import jakarta.validation.*;
import application.repository.BaseEntityRepository;
import application.service.BaseEntityService;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;


public class BaseEntityServiceImpl<U extends BaseEntityRepository<T, ID>,
        T extends BaseEntity<ID>, ID extends Serializable> implements BaseEntityService<T, ID> {

    protected final Validator validator;

    protected final U repository;


    public BaseEntityServiceImpl(Validator validator, U repository) {
        this.validator = validator;
        this.repository = repository;
    }


    @Override
    public T save(T entity) {
        try {
            validate(entity);
            return repository.save(entity);
        } catch (ValidationException e) {
            throw new ValidationException("Validation failed: " + e.getMessage(), e);
        }
    }


    @Override
    public T update(T newEntity) {
        try {
            validate(newEntity);
            return repository.save(newEntity);
        } catch (ValidationException e) {
            throw new ValidationException("Validation failed: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteById(ID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else throw new ValidationException("This id does not exist");

    }

    @Override
    public Boolean existsById(ID id) {
        if (id == null)
            throw new ValidationException("Id must not be null");
        return repository.existsById(id);
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }

    public void validate(T entity) {
        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<T> violation : violations) {
                sb.append(violation.getMessage()).append("\n");
            }
            throw new ConstraintViolationException(sb.toString(), violations);
        }
    }


}
