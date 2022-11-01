package SEForum.seforumbackend.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Arrays;
import java.util.Objects;

@Document
@Getter
@Setter
public class Comment {

    @Id
    public int id;
    @Field
    public String creationTime;
    @Field
    public String content;
    @Field
    public User user;
    @Field
    public Post post;
    @Field
    public Comment comment;
    @Field
    public String optional;

    public Comment() {
    }

    public Comment(String creationTime, String content, User user, Post post, Comment comment, String... optional) {
        this.creationTime = creationTime;
        this.content = content;
        this.user = user;
        this.post = post;
        this.comment = comment;
        this.optional = optional != null ? Arrays.toString(optional) : "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment1 = (Comment) o;
        return creationTime.equals(comment1.creationTime) && content.equals(comment1.content) && user.equals(comment1.user) && post.equals(comment1.post) && comment.equals(comment1.comment) && Objects.equals(optional, comment1.optional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creationTime, content, user, post, comment, optional);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", creationTime='" + creationTime + '\'' +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", post=" + post +
                ", comment=" + comment +
                ", optional='" + optional + '\'' +
                '}';
    }
}
