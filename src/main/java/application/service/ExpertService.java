package application.service;

import application.dto.OfferCreationDto;
import application.dto.ReportCustomerByOrderDTO;
import application.dto.ReportExpertByOrderDTO;
import application.dto.ViewScoreExpertDto;
import application.dto.projection.UserOrderCount;
import application.entity.Comment;
import application.entity.Offer;
import application.entity.users.Expert;

import java.util.List;
import java.util.Set;

public interface ExpertService extends UserService<Expert>{
    Boolean havePermissionExpertToServices(Integer expertId);
    Offer sendOffer(OfferCreationDto offerCreationDto,Integer expertId);
    List<ViewScoreExpertDto> viewScores(Integer expertId);
    Integer viewScore(Integer expertId);
    Boolean validateVerificationToken(String token);
    String sendVerificationToken(String email);

    List<UserOrderCount> getExpertOrderCounts();

    Set<ReportExpertByOrderDTO> getExpertOffers(Integer expertId);
    Integer getCreditFindByExpertId(Integer expertId);



}
