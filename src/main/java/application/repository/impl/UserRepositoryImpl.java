package application.repository.impl;

import application.entity.users.Users;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import application.repository.UserRepository;

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

    @Override
    public Boolean updatePassword(String email, String newPassword) {
        entityManager.getTransaction().begin();
        String query = """
                update %s p set p.profile.password =?2 where p.profile.email= ?1
                """.formatted(getEntityClass().getName());
        Query updateQuery = entityManager.createQuery(query);
        updateQuery.setParameter(1, email);
        updateQuery.setParameter(2, newPassword);
        int result = updateQuery.executeUpdate();
        entityManager.getTransaction().commit();
        return result>0;
    }
}