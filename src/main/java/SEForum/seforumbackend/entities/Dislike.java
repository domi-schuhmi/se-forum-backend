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
public class Dislike {

    @Id
    public int id;
    @Field
    public String creationTime;

    @Field
    public User user;
    @Field
    public String post;
    @Field
    public String optional;

    public Dislike() {
    }

    public Dislike(String creationTime, User user, String post, String... optional) {
        this.creationTime = creationTime;
        this.user = user;
        this.post = post;
        this.optional = optional != null ? Arrays.toString(optional) : "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dislike dislike = (Dislike) o;
        return creationTime.equals(dislike.creationTime) && user.equals(dislike.user) && post.equals(dislike.post) && Objects.equals(optional, dislike.optional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creationTime, user, post, optional);
    }

    @Override
    public String toString() {
        return "Dislike{" +
                "id=" + id +
                ", creationTime='" + creationTime + '\'' +
                ", user=" + user +
                ", post=" + post +
                ", optional='" + optional + '\'' +
                '}';
    }
}
