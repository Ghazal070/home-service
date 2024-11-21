package application.repository;

import application.entity.Authority;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Repository
public interface AuthorityRepository extends  BaseEntityRepository<Authority,Integer>{

    Boolean existsByName(String name);

    Set<Authority> findAllByNameIsIn(Collection<String> names);
}
