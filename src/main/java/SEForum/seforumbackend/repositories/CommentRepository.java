package SEForum.seforumbackend.repositories;

import SEForum.seforumbackend.entities.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String> {

}
