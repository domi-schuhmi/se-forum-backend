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
public class Like {

    @Id
    public int id;
    @Field
    public String creationTime;
    @Field
    public User user;
    @Field
    public Post post;
    @Field
    public String optional;

    public Like() {
    }

    public Like(String creationTime, User user, Post post, String... optional) {
        this.creationTime = creationTime;
        this.user = user;
        this.post = post;
        this.optional = optional != null ? Arrays.toString(optional) : "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Like like = (Like) o;
        return creationTime.equals(like.creationTime) && user.equals(like.user) && post.equals(like.post) && Objects.equals(optional, like.optional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creationTime, user, post, optional);
    }

    @Override
    public String toString() {
        return "Like{" +
                "id=" + id +
                ", creationTime='" + creationTime + '\'' +
                ", user=" + user +
                ", post=" + post +
                ", optional='" + optional + '\'' +
                '}';
    }
}
