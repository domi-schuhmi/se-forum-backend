package SEForum.seforumbackend.controller;

import SEForum.seforumbackend.entities.*;
import SEForum.seforumbackend.repositories.CommentRepository;
import SEForum.seforumbackend.repositories.LikeRepository;
import SEForum.seforumbackend.repositories.PostRepository;
import SEForum.seforumbackend.repositories.UserRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.*;

import static java.util.UUID.randomUUID;

@RestController()
@RequestMapping("/post")
@CrossOrigin(origins = "*")
@Slf4j
public class PostController {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;


    @Autowired
    public PostController(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository, LikeRepository likeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;

    }

    @GetMapping("/all")
    public List<Post> getAllPosts() {
        List<Post> result = postRepository.findAll();
        for (Post entity : result) {
            log.info(entity.toString());
        }
        return result;
    }


    @GetMapping("/{postId}")
    public Post getPostById(@PathVariable String postId) {
        var result = postRepository.findById(postId);
        return result.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with id " + postId + " does not exist"));
    }

    /**
     * Method to add a new post to the db
     * We ignore the id that is set and generate a new UUID
     *
     * @param newPost
     * @return
     */
    @PostMapping("/add")
    public Post addPost(@RequestBody PostDTO newPost) {

        if (newPost == null
                || newPost.getTitle() == null
                || newPost.getContent() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Post was invalid");
        }

        return addNewPost(newPost);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAll() {
        this.postRepository.deleteAll();
    }

    @DeleteMapping("/deletePost")
    public void deletePost(@RequestBody PostDTO deletedPost) {
        Optional<Post> optional = postRepository.findById(deletedPost.getId().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Must specify id of post to delete")));
        postRepository.delete(optional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with id " + deletedPost.getId().get() + " to delete was not found")));
    }

    @PostMapping("/update")
    public Post updatePost(@RequestBody PostDTO changedPost) {
        Optional<Post> optionalPost = postRepository.findById(changedPost.getId().orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Must specify id of post to update")));
        Optional<User> optionalUser = userRepository.findById(optionalPost
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not fin post with id " + changedPost.getId().get()))
                .getUser()
                .getId());

        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User does not exist");
        }

        Post post = new Post(
                optionalPost.get().getId(),
                optionalPost.get().getCreationTime(),
                changedPost.getTitle(),
                changedPost.getContent(),
                optionalUser.get(),
                buildCommentsRecursively(changedPost.getComments(), optionalPost.get().getId()),
                changedPost.getLikes(),
                changedPost.getDislikes(),
                changedPost.getOptional()
        );

        log.info("Updating post with id {} to: {}", optionalPost.get().getId(), post);

        return postRepository.save(post);
    }


    private Optional<Comment> findCommentById(@NonNull List<Comment> comments, @NonNull String commentId){
        log.info("Looking for comment with id {}", commentId);
        for (var comment : comments) {
            log.info("Checking if comment {} of post matches commentId {}", comment, commentId);
            if (comment.getId().equals(commentId)) {
                return Optional.of(comment);
            }else {
                var commentOptional = findCommentById(comment.getSubcomments(),commentId);
                if(commentOptional.isPresent()) return commentOptional;
            }
        }
        return Optional.empty();
    }


    @PostMapping("/{postId}/comment")
    public Post addCommentToPost(@PathVariable String postId, @RequestBody CommentDTO commentDTO) {
        var postOptional = postRepository.findById(postId);
        var post = postOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with id " + postId + " does not exist"));
        var comment = mapCommentDtoToComment(commentDTO, post.getId());
        post.comments.add(comment);
        log.info("Added comment with id {} to post with id {}", comment.getId(), post.getId());
        return postRepository.save(post);
    }

    @PostMapping("/{postId}/comment/{commentId}")
    public Post addSubComment(@PathVariable String postId, @PathVariable String commentId, @RequestBody CommentDTO commentDTO) {
        var postOptional = postRepository.findById(postId);
        var post = postOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with id " + postId + " does not exist"));

        var comment = mapCommentDtoToComment(commentDTO, post.getId());

        var commentOptional = findCommentById(post.getComments(),commentId);



        if(commentOptional.isPresent()){
            log.info("Found comment with repository: {}",commentOptional.get());
            commentOptional.get().getSubcomments().add(comment);
            return postRepository.save(post);
        }

        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
//        // append comment to comment or sub-comment specified by id
//        var updatedPost = appendCommentToCommentOrSubComment(post, commentId, comment);
//        log.info("Added sub-comment with id {} to post with id {}", comment.getId(), updatedPost.getId());
//
//
//
//        return updatedPost;

//        return postRepository.save(updatedPost);
    }

    @PutMapping("/like")
    public Post like(@RequestBody Like likeDTO) {
        var postOptional = postRepository.findById(likeDTO.post);
        var post = postOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with id " + likeDTO.post + " does not exist"));
        var like = mapLikeDTOtoLike(likeDTO);

        Like previousLike = null;
        for (Like l : post.likes ) {
            if(l.getUser().id.equals(like.getUser().id)) {
                previousLike = l;
                break;
            }
        }

        for (Dislike dl : post.dislikes ) {
            if(dl.getUser().id.equals(like.getUser().id)) {
                post.dislikes.remove(dl);
                break;
            }
        }

        if(previousLike==null) {
            post.likes.add(like);
        } else {
            post.likes.remove(previousLike);
        }
        return postRepository.save(post);
    }




    @PutMapping("/dislike")
    public Post like(@RequestBody Dislike dislikeDTO) {
        var postOptional = postRepository.findById(dislikeDTO.post);
        var post = postOptional.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Post with id " + dislikeDTO.post + " does not exist"));
        var dislike = mapDislikeDTOtoLike(dislikeDTO);

        Dislike previousDislike = null;
        for (Dislike dl : post.dislikes ) {
            if(dl.getUser().id.equals(dislike.getUser().id)) {
                previousDislike = dl;
                break;
            }
        }

        for (Like l : post.likes ) {
            if(l.getUser().id.equals(dislike.getUser().id)) {
                post.likes.remove(l);
                break;
            }
        }

        if(previousDislike==null) {
            post.dislikes.add(dislike);
        } else {
            post.dislikes.remove(previousDislike);
        }
        return postRepository.save(post);
    }

    private Post appendCommentToCommentOrSubComment(@NonNull Post post, @NonNull String commentId, @NonNull Comment commentToAdd) {

        log.info("hey");
        for (var comment : post.comments) {
            log.info("Checking if comment {} of post matches commentId {}", comment, commentId);
            if (comment.getId().equals(commentId)) {
                comment.subcomments.add(comment);
                return post;
            }  else if (!comment.getSubcomments().isEmpty()
                    && appendCommentToSubCommentRecursively(comment, commentId, commentToAdd)){
                return post;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Comment with id " + commentId
                + " on post with id " + post.getId()
                + " doesn't exist");
    }

    private boolean appendCommentToSubCommentRecursively(@NonNull Comment comment, @NonNull String commentId, @NonNull Comment commentToAdd) {

        for (var subComment : comment.subcomments) {
            if (subComment.getId().equals(commentId)) {
                subComment.subcomments.add(commentToAdd);
                return true;
            }
            else if (!subComment.getSubcomments().isEmpty()) {
                return appendCommentToSubCommentRecursively(subComment, commentId, commentToAdd);
            }
        }
        return false;
    }


    private Post addNewPost(PostDTO newPost) {
        Optional<User> optionalUser = userRepository.findById(newPost.getUserId());
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User does not exist");
        }
        String newPostId = randomUUID().toString();

        return optionalUser.map(value -> postRepository.save(
                new Post(
                        newPostId,
                        Instant.now().toString(),
                        newPost.getTitle(),
                        newPost.getContent(),
                        value,
                        newPost.getComments() == null
                                ? List.of()
                                : buildCommentsRecursively(newPost.getComments(), newPostId),
                        newPost.getLikes(),
                        newPost.getDislikes(),
                        newPost.getOptional()
                )
        )).orElse(null);
    }

    /**
     * Maps a CommentDto from the frontend to a Comment object of the DB
     *
     * @throws ResponseStatusException if the user id of the comment is not set or if the user does not exist
     */
    private Comment mapCommentDtoToComment(@NonNull CommentDTO commentDTO, @NonNull String postId) {

        if (commentDTO.getUser() == null) {
            log.info("user of comment with text: {} is null", commentDTO.getContent());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id of comment was missing");
        }

        Optional<User> optionalUser = userRepository.findById(commentDTO.getUser());
        var user = optionalUser.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User id of comment didn't exist"));

        return new Comment(
                UUID.randomUUID().toString(),
                Instant.now().toString(),
                commentDTO.getContent(),
                user,
                postId,
                List.of(),
                commentDTO.getOptional());
    }

    private Like mapLikeDTOtoLike(@NonNull Like like) {

        if (like.getUser() == null) {
            log.info("user of like is null");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id of like was missing");
        }
        return new Like(
                Instant.now().toString(),
                like.user,
                like.getPost(),
                like.getOptional());
    }

    private Dislike mapDislikeDTOtoLike(@NonNull Dislike dislike) {

        if (dislike.getUser() == null) {
            log.info("user of like is null");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User id of like was missing");
        }
        return new Dislike(
                Instant.now().toString(),
                dislike.user,
                dislike.getPost(),
                dislike.getOptional());
    }

    private List<Comment> buildCommentsRecursively(List<CommentDTO> commentsDTO, @NonNull String postId) {
        if (commentsDTO == null) {
            return List.of();
        }

        ArrayList<Comment> comments = new ArrayList<>();
        for (CommentDTO comment : commentsDTO) {

            if (comment.getUser() == null) {
                log.info("user of comment with text: {} is null", comment.getContent());
                continue;
            }

            Optional<User> optionalUser = userRepository.findById(comment.getUser());
            comments.add(
                    new Comment(
                            UUID.randomUUID().toString(),
                            comment.getCreationTime(),
                            comment.getContent(),
                            optionalUser.orElseThrow(),
                            postId,
                            buildCommentsRecursively(comment.getSubcomments(), postId),
                            comment.getOptional()));
        }
        return comments;
    }
}
