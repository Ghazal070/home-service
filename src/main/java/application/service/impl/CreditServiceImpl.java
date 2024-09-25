package application.service.impl;

import application.entity.Credit;
import application.entity.Order;
import application.repository.CreditRepository;
import application.repository.OrderRepository;
import application.service.CreditService;
import application.service.OrderService;

public class CreditServiceImpl extends BaseEntityServiceImpl<CreditRepository, Credit, Integer> implements CreditService {

    public CreditServiceImpl(CreditRepository repository) {
        super(repository);
    }
}
