package application.repository;

import application.dto.ViewScoreExpertDto;
import application.dto.projection.UserOrderCount;
import application.entity.Duty;
import application.entity.Order;
import application.entity.users.Expert;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertRepository extends UserRepository<Expert> {


    @Query(nativeQuery = true,
        value = """  
        SELECT  
            c.id AS userId,  
            c.dtype AS role,  
            c.date_time_submission AS registerDate,  
            COUNT(o.id) AS totalRequests,  
            COUNT(CASE WHEN ord.order_status IN ('Done', 'Payed') THEN 1 END) AS doneOrders  
        FROM  
            users c  
        LEFT JOIN  
            offer o ON c.id = o.expert_id 
        LEFT JOIN  
            orders_home_service ord ON ord.id = o.order_id  
        WHERE  
            c.dtype = 'Expert'   
        GROUP BY  
            c.id, c.dtype, c.date_time_submission  
        ORDER BY  
            c.id  
    """)
    List<UserOrderCount> getExpertOrderCounts();

    @Query("SELECT c.credit.amount FROM Expert c WHERE c.id = :expertId")
    Integer getCreditFindByExpertId(Integer expertId);

}
