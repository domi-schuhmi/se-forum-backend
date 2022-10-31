package SEForum.seforumbackend.repositories;

import SEForum.seforumbackend.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    public User findByPasswordHash(String passwordHash);
    public List<User> findByUsername(String username);

}
