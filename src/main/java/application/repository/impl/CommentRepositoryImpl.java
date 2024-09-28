package application.repository.impl;

import application.entity.Comment;
import application.entity.Comment_;
import application.repository.CommentRepository;
import application.repository.DatabaseAccess;
import jakarta.persistence.EntityManager;

public class CommentRepositoryImpl extends BaseEntityRepositoryImpl<Comment, Integer>
        implements CommentRepository {

    public CommentRepositoryImpl(DatabaseAccess<Comment, Integer> databaseAccess) {
        super(databaseAccess);
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
