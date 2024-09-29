package application.repository;

import application.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends BaseEntityRepository<Order,Integer>{

}
