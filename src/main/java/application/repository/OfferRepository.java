package application.repository;

import application.entity.Offer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OfferRepository extends BaseEntityRepository<Offer, Integer> {

    @Query("""
            select f from Offer f 
            join f.order o 
            join o.customer c
            where c.id=:customerId and o.id= :orderId
            order by f.expert.score
            """)
    Set<Offer> getOfferByCustomerIdOrderByScoreExpert(@Param("customerId") Integer customerId,@Param("orderId")Integer orderId);

    @Query("""
            select f from Offer f 
            join f.order o 
            join o.customer c
            where c.id=:customerId and o.id= :orderId
            order by f.order.priceOrder
            """)
    Set<Offer> getOfferByCustomerIdOrderByPriceOrder(@Param("customerId") Integer customerId,@Param("orderId")Integer orderId);

    @Query("update Offer f set f.order.orderStatus = :orderStatus where f.id = :offerId")
    Offer updateByOrderStatus(@Param("orderStatus") String orderStatus,@Param("offerId") Integer offerId);
}
