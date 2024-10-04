package application.service;

import application.dto.OfferCreation;
import application.entity.Duty;
import application.entity.Offer;
import application.entity.Order;
import application.entity.users.Expert;

import java.util.List;

public interface ExpertService extends UserService<Expert>{
    Boolean havePermissionExpertToServices(Expert expert);
    Offer sendOffer(OfferCreation offerCreation);

}
