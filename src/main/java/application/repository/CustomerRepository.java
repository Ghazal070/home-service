package application.repository;

import application.dto.projection.UserOrderCount;
import application.entity.users.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerRepository extends UserRepository<Customer> {

    @Query(nativeQuery = true,
            value = """  
            SELECT  
                c.id AS userId,  
                c.dtype AS role,  
                c.date_time_submission AS registerDate,  
                COUNT(o.id) AS totalRequests,  
                COUNT(CASE WHEN o.order_status = 'Done' THEN 1 END) AS doneOrders  
            FROM  
                users c  
            LEFT JOIN  
                orders_home_service o ON c.id = o.customer_id   
            WHERE  
                c.dtype = 'Customer'   
            GROUP BY  
                c.id, c.dtype, c.date_time_submission  
            ORDER BY  
                c.id  
        """)
    List<UserOrderCount> getCustomerOrderCounts();

    @Query("SELECT c.credit.amount FROM Customer c WHERE c.id = :customerId")
    Integer getCreditFindByCustomerId(Integer customerId);
}
