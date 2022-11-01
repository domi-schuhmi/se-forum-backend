package SEForum.seforumbackend.repositories;

import SEForum.seforumbackend.entities.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PostRepository extends MongoRepository<Post, Integer> {
}
