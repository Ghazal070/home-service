package application.service.impl;

import application.dto.OfferCreationDto;
import application.dto.ViewScoreExpertDto;
import application.entity.Comment;
import application.entity.Offer;
import application.entity.Order;
import application.entity.enumeration.ExpertStatus;
import application.entity.enumeration.OrderStatus;
import application.entity.users.Expert;
import application.service.*;
import jakarta.validation.ValidationException;
import application.repository.ExpertRepository;
import application.util.AuthHolder;
import jakarta.validation.Validator;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class ExpertServiceImpl extends UserServiceImpl<ExpertRepository, Expert> implements ExpertService {

    private final OrderService orderService;
    private final OfferService offerService;
    private final CommentService commentService;
    private final JavaMailSender mailSender;

    public ExpertServiceImpl(Validator validator, ExpertRepository repository, AuthHolder authHolder, PasswordEncoder passwordEncoder, OrderService orderService, OfferService offerService, CommentService commentService, JavaMailSender mailSender) {
        super(validator, repository, authHolder, passwordEncoder);
        this.orderService = orderService;
        this.offerService = offerService;
        this.commentService = commentService;
        this.mailSender = mailSender;
    }

    @Override
    public Boolean havePermissionExpertToServices(Integer expertId) {
        Optional<Expert> expert = repository.findById(expertId);
        if (expert.isPresent()) {
            if (!expert.get().getEnabled()) {
                throw new ValidationException("Account is not active.");
            }
            return expert.get().getExpertStatus().equals(ExpertStatus.Accepted);
        }
        return false;
    }

    @Override
    public Offer sendOffer(OfferCreationDto offerCreationDto, Integer expertId) {
        Optional<Expert> expert = repository.findById(expertId);
        if (expert.isPresent()) {
            if (!expert.get().getEnabled()) {
                throw new ValidationException("Account is not active.");
            }
        }
        Optional<Order> order = orderService.findById(offerCreationDto.getOrderId());
        if (order.isPresent()) {
            Set<Order> orderList = orderService.getOrdersForExpertWaitingOrChoosing(expertId);
            boolean existOrderInList = false;
            for (Order orderTemp : orderList) {
                if (order.get().equals(orderTemp)) {
                    existOrderInList = true;
                    break;
                }
            }
            Integer basePrice = order.get().getDuty().getBasePrice();
            if (!existOrderInList) {
                throw new ValidationException("This order is not in list expert order or not waiting");
            }
            if (basePrice != null && basePrice <= offerCreationDto.getPriceOffer()) {
                Offer offer = Offer.builder()
                        .order(order.get())
                        .expert(expert.get())
                        .dateTimeStartWork(offerCreationDto.getDateTimeStartWork())
                        .lengthDays(offerCreationDto.getLengthDays())
                        .priceOffer(offerCreationDto.getPriceOffer())
                        .build();
                Offer saveOffer = offerService.save(offer);
                if (saveOffer != null) {
                    if (order.get().getOrderStatus().equals(OrderStatus.ExpertOfferWanting)) {
                        order.get().setOrderStatus(OrderStatus.ExpertChooseWanting);
                        orderService.update(order.get());
                    }
                    return saveOffer;
                } else throw new ValidationException("Error in saving offer");
            } else throw new ValidationException("Base price greater than your offer");
        } else throw new ValidationException("Order is null");
    }

    @Override
    public List<ViewScoreExpertDto> viewScores(Integer expertId) {
        Optional<Expert> expert = repository.findById(expertId);
        if (expert.isPresent()) {
            if (!expert.get().getEnabled()) {
                throw new ValidationException("Account is not active.");
            }
        }
        List<Comment> commentByExpertId = commentService.getCommentByExpertId(expertId);
        List<ViewScoreExpertDto> viewScoreExpertDtoList = new ArrayList<>();
        commentByExpertId.forEach(c ->
                viewScoreExpertDtoList.add(
                        ViewScoreExpertDto.builder()
                                .orderId(c.getOffer().getOrder().getId())
                                .offerId(c.getOffer().getId())
                                .score(c.getScore())
                                .build()
                )
        );
        return viewScoreExpertDtoList;
    }

    @Override
    public Integer viewScore(Integer expertId) {
        Optional<Expert> expert = repository.findById(expertId);
        if (expert.isPresent()) {
            return expert.get().getScore();
        }
        throw new ValidationException("Expert id not exist");
    }

    @Override
    public Boolean validateVerificationToken(String token) {
        Expert user = repository.findByVerificationToken(token).orElse(null);
        if (user == null || user.getExpiryDateVerificationToken().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Invalid or expire token");
        }
        user.setEnabled(true);
        if (user.getExpertStatus() == ExpertStatus.New) {
            user.setExpertStatus(ExpertStatus.AcceptWaiting);
        }
        repository.save(user);
        user.setVerificationToken(null);
        return true;
    }
    @Override
    public String sendVerificationToken(String email) {
        String token = UUID.randomUUID().toString();
        String confirmationUrl = "http://localhost:8080/v1/users/experts/activate?token=" + token;

        String subject = "Activate Your Account";
        String text = "Email Verification, Click the link to verify your email::\n" + confirmationUrl;
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
        return token;
    }
}
