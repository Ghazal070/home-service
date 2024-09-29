package application.repository;

import application.entity.Comment;
import application.entity.Credit;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditRepository extends BaseEntityRepository<Credit,Integer>{

}
