package application.repository;

import application.entity.Duty;
import application.entity.Order;
import application.entity.users.Expert;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertRepository extends UserRepository<Expert> {



}
