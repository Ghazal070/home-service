package application.repository;

import application.entity.users.Expert;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpertRepository extends UserRepository<Expert>{

}
