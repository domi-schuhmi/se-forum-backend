package SEForum.seforumbackend.repositories;

import SEForum.seforumbackend.entities.Dislike;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DislikeRepository extends MongoRepository<Dislike, Integer> {
}
