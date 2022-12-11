package SEForum.seforumbackend.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Document
@Data
@NoArgsConstructor
public class Comment {

    @Id
    public String id;
    @Field
    public String creationTime;
    @Field
    public String content;
    @DBRef
    public User user;
    @Field
    public String post;
    @Field
    public List<Comment> subcomments;
    @Field
    public String optional;

    public Comment(String id, String creationTime, String content, User user, String postId, List<Comment> subcomments, String... optional) {
        this.id = id;
        this.creationTime = creationTime;
        this.content = content;
        this.user = user;
        this.post = postId;
        this.subcomments = subcomments;
        this.optional = optional != null ? Arrays.toString(optional) : "";
    }
}
