package SEForum.seforumbackend.entities;

import java.util.Arrays;

public class CommentDTO {

    public int id;
    public String creationTime;
    public String content;
    public int user;
    public int post;
    public CommentDTO[] subcomments;
    public String optional;

    public CommentDTO() {
    }

    public CommentDTO(String creationTime, String content, int user, int post, CommentDTO[] comments, String... optional) {
        this.creationTime = creationTime;
        this.content = content;
        this.user = user;
        this.post = post;
        this.subcomments = comments;
        this.optional = optional != null ? Arrays.toString(optional) : "";
    }

}
