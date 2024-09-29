package application.repository;

import application.entity.Comment;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends BaseEntityRepository<Comment,Integer>{

}
