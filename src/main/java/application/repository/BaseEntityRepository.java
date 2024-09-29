package application.repository;

import application.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public interface BaseEntityRepository <T extends BaseEntity<ID>, ID extends Serializable> extends JpaRepository<T,ID> {

}
