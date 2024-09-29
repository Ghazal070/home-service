package application.repository;

import application.entity.users.Users;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository<T extends Users> extends BaseEntityRepository<T,Integer>{

    @Query("select u from Users u where u.profile.email=:email and u.profile.password=:password")
    Optional<Users> login(String email, String password);

    @Modifying
    @Transactional
    @Query("update Users p set p.profile.password =:newPassword where p.profile.email=:email")
    int updatePassword(String email,String newPassword);

    @Query("select count(u)>0 from Users u where u.profile.email=:uniqField")
    Boolean containByUniqField(String uniqField);

}
