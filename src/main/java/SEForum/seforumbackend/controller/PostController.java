package SEForum.seforumbackend.controller;

import SEForum.seforumbackend.entities.Post;
import SEForum.seforumbackend.entities.PostDTO;
import SEForum.seforumbackend.entities.User;
import SEForum.seforumbackend.repositories.PostRepository;
import SEForum.seforumbackend.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/post")
@CrossOrigin(origins = "*")
public class PostController {
    private static int counter = 0;

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final Logger logger;


    @Autowired
    public PostController(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.logger = (Logger) LoggerFactory.getLogger(PostController.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(PostController.class, args);
    }

    @GetMapping("/all")
    public List<Post> getList() {
        List<Post> result = postRepository.findAll();
        for (Post entity : result) {
            this.logger.info(entity.toString());
        }
        return result;
    }

    @PostMapping("/add")
    public Post getList(@RequestBody PostDTO newPost) {
        if (newPost == null || newPost.title == null || newPost.user < 0 || newPost.content == null || newPost.creationTime == null) {
            throw new IllegalArgumentException();
        }
        if (newPost.id != -1) {
            return getUser(newPost);
        } else {
            counter++;
            newPost.id = counter;
            return getUser(newPost);
        }
    }

    private Post getUser(PostDTO newPost) {
        Optional<User> user = userRepository.findById(newPost.user);
        if (user.isPresent()) {
            return postRepository.save(new Post(newPost.id, newPost.creationTime, newPost.title, newPost.content, user.get(), newPost.comment, newPost.likes, newPost.dislikes, newPost.optional));
        }
        return null;
    }

}