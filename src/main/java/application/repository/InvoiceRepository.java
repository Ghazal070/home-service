package application.repository;

import application.entity.Invoice;
import application.entity.Offer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface InvoiceRepository extends BaseEntityRepository<Invoice, Integer> {

    Boolean existsInvoiceByOrderId(Integer orderId);

}
