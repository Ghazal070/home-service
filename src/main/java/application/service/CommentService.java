package application.service;

import application.dto.ViewScoreExpertDto;
import application.entity.Comment;
import application.entity.Offer;
import application.entity.Order;

import java.util.List;

public interface CommentService extends BaseEntityService<Comment,Integer>{

    void submitCustomerComment(Offer offer,String commentContent, Integer score);

    List<Comment> getCommentByExpertId(Integer expertId);
}
