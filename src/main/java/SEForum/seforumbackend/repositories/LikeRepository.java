package SEForum.seforumbackend.repositories;

import SEForum.seforumbackend.entities.Like;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LikeRepository extends MongoRepository<Like, Integer> {
}
