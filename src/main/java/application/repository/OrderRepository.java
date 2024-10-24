package application.repository;

import application.entity.Order;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OrderRepository extends BaseEntityRepository<Order, Integer>, JpaSpecificationExecutor<Order> {

    @Query("""  
            SELECT o FROM Order o  
            WHERE o.duty IN (  
                SELECT d FROM Duty d  
                JOIN d.experts e  
                WHERE e.id = :expertId  
            )  
            AND (o.orderStatus = 'ExpertOfferWanting' OR o.orderStatus = 'ExpertChooseWanting')  
            """)
    Set<Order> getOrdersForExpertWaitingOrChoosing(@Param("expertId") Integer expertId);


    Set<Order> findAllByCustomerId(Integer customerId);

}
