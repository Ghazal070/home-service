package application.service.impl;

import application.entity.Invoice;
import application.entity.Offer;
import application.repository.InvoiceRepository;
import application.repository.OfferRepository;
import application.service.InvoiceService;
import application.service.OfferService;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class InvoiceServiceImpl extends BaseEntityServiceImpl<InvoiceRepository, Invoice, Integer> implements InvoiceService {
    public InvoiceServiceImpl(Validator validator, InvoiceRepository repository) {
        super(validator, repository);
    }

    @Override
    public Boolean existByOrderId(Integer orderId) {
        return repository.existsInvoiceByOrderId(orderId);
    }
}
