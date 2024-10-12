package application.service.impl;

import application.entity.Comment;
import application.repository.CommentRepository;
import application.service.CommentService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl extends BaseEntityServiceImpl<CommentRepository, Comment, Integer> implements CommentService {

    public CommentServiceImpl(Validator validator, CommentRepository repository) {
        super(validator, repository);
    }
}
