package SEForum.seforumbackend.repositories;

import SEForum.seforumbackend.entities.Post;
import SEForum.seforumbackend.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends MongoRepository<Post, String> {

    public List<Post> findPostsByUser_Id(String userId);
}
