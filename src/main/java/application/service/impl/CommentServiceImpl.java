package application.service.impl;

import application.entity.Comment;
import application.entity.Offer;
import application.entity.users.Expert;
import application.repository.CommentRepository;
import application.service.CommentService;
import application.service.OfferService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl extends BaseEntityServiceImpl<CommentRepository, Comment, Integer> implements CommentService {

    private final OfferService offerService;

    public CommentServiceImpl(Validator validator, CommentRepository repository, OfferService offerService) {
        super(validator, repository);
        this.offerService = offerService;
    }

    @Override
    public void submitCustomerComment(Offer offer, String commentContent, Integer score) {
        Expert expert = offer.getExpert();
        expert.setScore(expert.getScore() + score);
        offerService.update(offer);
        Comment comment = Comment.builder()
                .offer(offer)
                .score(score)
                .content(commentContent)
                .build();
        repository.save(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getCommentByExpertId(Integer expertId) {
        return repository.getCommentsByExpertId(expertId);
    }
}