package SEForum.seforumbackend.controller;

import SEForum.seforumbackend.entities.Post;
import SEForum.seforumbackend.entities.User;
import SEForum.seforumbackend.repositories.PostRepository;
import SEForum.seforumbackend.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController()
@RequestMapping("/user")
@CrossOrigin(origins = "*")
@Slf4j
public class UserController {

    private final UserRepository userrepo;
    private final PostRepository postRepository;

    @Autowired
    public UserController(UserRepository userrepo, PostRepository postRepository) {
        this.userrepo = userrepo;
        this.postRepository = postRepository;
    }

    @GetMapping("/all")
    public List<User> getList() {
        List<User> result = userrepo.findAll();
        for (User user : result) {
            log.info(user.toString());
        }
        return result;
    }

    /**
     * Adds a new user to the DB
     */
    @PostMapping("/add")
    public User addUser(@RequestBody User newUser) {
        if (newUser == null
                || newUser.username == null
                || newUser.passwordHash == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User was not valid");
        }

        if (userrepo.findByUsername(newUser.username).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        }

        log.info(newUser.toString());
        newUser.setId(UUID.randomUUID().toString());
        return userrepo.save(newUser);
    }

    /**
     * Finds a user by username
     */
    @GetMapping("/byUsername")
    public User getUserByUsername(@RequestParam String username) {
        var userOptional = userrepo.findByUsername(username);
        return userOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @GetMapping("/{userId}/posts")
    public List<Post> getPostsOfUser(@PathVariable String userId) {

        // check if user exists
        Optional<User> userOptional = userrepo.findById(userId);
        if (userOptional.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist");

        return postRepository.findPostsByUser_Id(userOptional.get().getId());
    }

    /**
     * Gets User by userId
     */
    @GetMapping("/{userId}")
    public User getUser(@PathVariable String userId) {
        // check if user exists
        Optional<User> userOptional = userrepo.findById(userId);
        return userOptional.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist"));
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll() {
        this.userrepo.deleteAll();
    }

}
