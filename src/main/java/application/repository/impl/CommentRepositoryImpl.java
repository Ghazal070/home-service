package application.repository.impl;

import application.entity.Comment;
import application.entity.Comment_;
import application.repository.CommentRepository;
import jakarta.persistence.EntityManager;

public class CommentRepositoryImpl extends BaseEntityRepositoryImpl<Comment, Integer>
        implements CommentRepository {
    public CommentRepositoryImpl(EntityManager entityManager) {
        super(entityManager);
    }

    @Override
    public Class<Comment> getEntityClass() {
        return Comment.class;
    }

    @Override
    public String getUniqueFieldName() {
        return Comment_.ID;
    }
}
