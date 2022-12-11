package SEForum.seforumbackend.entities;

import lombok.Data;

import java.util.List;
import java.util.Optional;


@Data
public class PostDTO {

    private Optional<String> id;
    private Optional<String> creationTime;
    private String title;
    private String content;
    private String userId;
    private List<CommentDTO> comments;
    private List<Like> likes;
    private List<Dislike> dislikes;
    private String optional;

}
