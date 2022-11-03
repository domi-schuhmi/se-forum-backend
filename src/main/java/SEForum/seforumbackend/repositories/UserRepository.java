package SEForum.seforumbackend.repositories;

import SEForum.seforumbackend.entities.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Integer> {
//    public User findByPasswordHash(String passwordHash);
//
//    public List<User> findByUsername(String username);
}
