package application.repository;

import application.entity.users.Admin;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends  UserRepository<Admin>{
}
