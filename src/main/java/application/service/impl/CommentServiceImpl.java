package application.service.impl;

import application.entity.Comment;
import application.entity.Order;
import application.repository.CommentRepository;
import application.repository.OrderRepository;
import application.service.CommentService;
import application.service.OrderService;

public class CommentServiceImpl extends BaseEntityServiceImpl<CommentRepository, Comment, Integer> implements CommentService {

    public CommentServiceImpl(CommentRepository repository) {
        super(repository);
    }
}
