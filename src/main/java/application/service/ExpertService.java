package application.service;

import application.dto.OfferCreationDto;
import application.dto.ViewScoreExpertDto;
import application.entity.Comment;
import application.entity.Offer;
import application.entity.users.Expert;

import java.util.List;

public interface ExpertService extends UserService<Expert>{
    Boolean havePermissionExpertToServices(Integer expertId);
    Offer sendOffer(OfferCreationDto offerCreationDto,Integer expertId);
    List<ViewScoreExpertDto> viewScore(Integer expertId);


}
