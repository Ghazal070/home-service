package application.repository;

import application.entity.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends  BaseEntityRepository<Role,Integer>{

    @EntityGraph(attributePaths = "authorities")
    Optional<Role> findByName(String name);
}
