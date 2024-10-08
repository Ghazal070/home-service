package application.service;

import application.dto.OfferCreationDto;
import application.entity.Offer;
import application.entity.users.Expert;

public interface ExpertService extends UserService<Expert>{
    Boolean havePermissionExpertToServices(Expert expert);
    Offer sendOffer(OfferCreationDto offerCreationDto,Integer expertId);

}
