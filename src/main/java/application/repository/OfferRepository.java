package application.repository;

import application.entity.Offer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface OfferRepository extends BaseEntityRepository<Offer,Integer>{

    @Query("""

""")
    Set<Offer> getOfferByCustomerIdOrderByScoreExpert(@Param("customerId") Integer customerId);
}
