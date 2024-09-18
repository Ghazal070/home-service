package repository.impl;

import entity.users.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import repository.UserRepository;

import java.util.List;

public abstract class UserRepositoryImpl<T extends Users> extends BaseEntityRepositoryImpl<T,Integer> implements UserRepository<T> {
    public UserRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Users login(String username, String password) {
        String query = """
                from %s p where p.profile.email= ?1 and p.profile.password= ?2
                """.formatted(getEntityClass().getName());
        TypedQuery<T> typedQuery = entityManager.createQuery(query, getEntityClass());
        typedQuery.setParameter(1, username);
        typedQuery.setParameter(2, password);
        List<T> resultList = typedQuery.getResultList();
        if(!resultList.isEmpty()) return resultList.get(0);
        return null;
    }

}
