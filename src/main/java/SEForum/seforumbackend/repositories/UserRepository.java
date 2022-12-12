package SEForum.seforumbackend.repositories;

import SEForum.seforumbackend.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
//    public User findByPasswordHash(String passwordHash);
//
    public Optional<User> findByUsername(String username);
}
