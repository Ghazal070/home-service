package application.repository;

import application.entity.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends BaseEntityRepository<Comment, Integer> {

    @Query(
            """
                    select c from Comment  c 
                    join c.offer o 
                    where o.expert.id = :expertId
                    """
    )
    List<Comment> getCommentsByExpertId(@Param("expertId") Integer expertId);
}
