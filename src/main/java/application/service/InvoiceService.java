package application.service;

import application.entity.Invoice;
import application.entity.Offer;

import java.util.Set;

public interface InvoiceService extends BaseEntityService<Invoice, Integer> {

    Boolean existByOrderId(Integer orderId);
}
