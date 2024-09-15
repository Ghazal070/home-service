package service.impl;

import entity.BaseEntity;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.*;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.yaml.snakeyaml.events.Event;
import repository.BaseEntityRepository;
import service.BaseEntityService;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class BaseEntityServiceImpl<U extends BaseEntityRepository<T,ID>,
        T extends BaseEntity<ID>,ID extends Serializable> implements BaseEntityService<T,ID> {


    protected final Validator validator;
    protected final U repository;
    ValidatorFactory factory = Validation.byProvider(HibernateValidator.class)
            .configure()
            .messageInterpolator(new ParameterMessageInterpolator())
            .buildValidatorFactory();

    public BaseEntityServiceImpl(U repository) {
        this.repository = repository;
        this.validator = factory.getValidator();
    }

    @Override
    public T save(T entity) {
        try {
            validate(entity);
            T existEntity = null;
            if (entity.getId() != null) {
                existEntity = repository.findById(entity.getId());
            }
            if (existEntity == null) {
                return repository.save(entity);
            } else {
                return existEntity;
            }
        } catch (ValidationException e) {
            throw new ValidationException("Validation failed: " + e.getMessage(), e);
        } catch (EntityExistsException e) {
            throw new RuntimeException("Entity already exists: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while saving the entity: " + e.getMessage(), e);
        }



    }



    @Override
    public T update(T newEntity) {
        return repository.update(newEntity);
    }

    @Override
    public void delete(ID id) {
        repository.delete(id);
    }

    public Boolean containById(ID id) {
        return repository.containById(id);
    }

    @Override
    public T findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public List<T> loadAll() {
        return repository.loadAll();
    }

    @Override
    public Boolean contain(T entity) {
        return repository.contain(entity);
    }

    @Override
    public T findByUniqId(String uniqId) {
        if (uniqId != null) return repository.findByUniqId(uniqId);
        else return null;
    }
    @Override
    public Boolean deleteByUniqId(String uniqId) {
        return repository.deleteByUniqId(uniqId);
    }


    private void validate(T entity) {
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
