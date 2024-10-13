package application.service.impl;

import application.entity.Card;
import application.entity.Credit;
import application.repository.CreditRepository;
import application.service.CreditService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

@Service
public class CreditServiceImpl extends BaseEntityServiceImpl<CreditRepository, Credit, Integer> implements CreditService {
    public CreditServiceImpl(Validator validator, CreditRepository repository) {
        super(validator, repository);
    }


}
