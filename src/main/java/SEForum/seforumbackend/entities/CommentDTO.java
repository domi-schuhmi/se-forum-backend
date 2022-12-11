package SEForum.seforumbackend.entities;

import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class CommentDTO {

    private String id;
    private String creationTime;
    private String content;
    private String user;
    private List<CommentDTO> subcomments;
    private String optional;

    public CommentDTO(String creationTime, String content, String userId, List<CommentDTO> comments, String... optional) {
        this.creationTime = creationTime;
        this.content = content;
        this.user = userId;
        this.subcomments = comments;
        this.optional = optional != null ? Arrays.toString(optional) : "";
    }

}
