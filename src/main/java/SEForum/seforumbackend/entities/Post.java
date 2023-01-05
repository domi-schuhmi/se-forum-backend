package SEForum.seforumbackend.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Document
@Data
@NoArgsConstructor
public class Post {

    @Id
    public String id;
    @Field
    public String creationTime;
    @Field
    public String title;
    @Field
    public String content;
    @Field
    public User user;
    @Field
    public List<Comment> comments;
    @Field
    public List<Like> likes;
    @Field
    public List<Dislike> dislikes;
    @Field
    public String optional;

    public Post(String id, String creationTime, String title, String content, User user, List<Comment> comment, List<Like> likes, List<Dislike> dislikes, String... optional) {
        this.id = id;
        this.creationTime = creationTime;
        this.title = title;
        this.content = content;
        this.user = user;
        this.comments = comment;
        this.likes = new ArrayList<>();
        this.dislikes = new ArrayList<>();
        this.optional = optional != null ? Arrays.toString(optional) : "";
    }
}
