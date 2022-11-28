package SEForum.seforumbackend.controller;

import SEForum.seforumbackend.entities.*;
import SEForum.seforumbackend.repositories.CommentRepository;
import SEForum.seforumbackend.repositories.PostRepository;
import SEForum.seforumbackend.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController()
@RequestMapping("/post")
@CrossOrigin(origins = "*")
public class PostController {
    private static int counter = -1;

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
    public Post addPost(@RequestBody PostDTO newPost) {
        if (counter == -1){
            OptionalInt maxID = this.getList().stream().mapToInt(p -> p.id ).max( );
            counter = maxID.getAsInt();
        }

        if (newPost == null || newPost.title == null || newPost.user < 0 || newPost.content == null || newPost.creationTime == null) {
            throw new IllegalArgumentException();
        }
        if (newPost.id != -1) {
            return addNewPost(newPost);
        } else {
            counter++;
            newPost.id = counter;
            return addNewPost(newPost);
        }
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll() {
        this.postRepository.deleteAll();
    }

    @DeleteMapping("/deletePost")
    public void deletePost(@RequestBody PostDTO deletedPost){
        Optional<Post> optional = postRepository.findById(deletedPost.id);
        postRepository.delete(optional.get());
    }

    @PostMapping("/updatePost")
    public Post updatePost(@RequestBody PostDTO changedPost){
//        Optional<Post> optionalPost = postRepository.findById(changedPost.id);
        Optional<User> optionalUser = userRepository.findById(changedPost.user);

        Post post = new Post(
                changedPost.id,
                changedPost.creationTime,
                changedPost.title,
                changedPost.content,
                optionalUser.get(),
                buildCommentArray(changedPost.comments),
                changedPost.likes,
                changedPost.dislikes,
                changedPost.optional
        );

        return postRepository.save(post);
    }

    private Post addNewPost(PostDTO newPost) {
        Optional<User> user = userRepository.findById(newPost.user);
        if (user.isPresent()) {
            return postRepository.save(
                    new Post(
                            newPost.id,
                            newPost.creationTime,
                            newPost.title,
                            newPost.content,
                            user.get(),
                            buildCommentArray(newPost.comments),
                            newPost.likes,
                            newPost.dislikes,
                            newPost.optional
                    )
            );
        }
        return null;
    }

    private Comment[] buildCommentArray(CommentDTO[] commentsDTO) {
        if (commentsDTO == null){
            return null;
        }

        ArrayList<Comment> comments = new ArrayList<>();
        for (CommentDTO comment : commentsDTO ) {
            Optional<User> optionalUser = userRepository.findById(comment.user);
            comments.add(new Comment( comment.creationTime, comment.content, optionalUser.get(), comment.post, buildCommentArray(comment.subcomments), comment.optional ));
        }

        Comment[] commentsArray = new Comment[comments.size()];

        return comments.toArray(commentsArray);
    }

}
