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
public class Post {

    @Id
    public int id;
    @Field
    public String creationTime;
    @Field
    public String title;
    @Field
    public String content;
    @Field
    public User user;
    @Field
    public Comment[] comment;
    @Field
    public Like[] likes;
    @Field
    public Dislike[] dislikes;
    @Field
    public String optional;

    public Post() {
    }

    public Post(int id, String creationTime, String title, String content, User user, Comment[] comment, Like[] likes, Dislike[] dislikes, String... optional) {
        this.id = id;
        this.creationTime = creationTime;
        this.title = title;
        this.content = content;
        this.user = user;
        this.comment = comment;
        this.likes = likes;
        this.dislikes = dislikes;
        this.optional = optional != null ? Arrays.toString(optional) : "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return creationTime.equals(post.creationTime) && title.equals(post.title) && content.equals(post.content) && user.equals(post.user) && Arrays.equals(comment, post.comment) && Arrays.equals(likes, post.likes) && Arrays.equals(dislikes, post.dislikes) && Objects.equals(optional, post.optional);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(creationTime, title, content, user);
        result = 31 * result + Arrays.hashCode(comment);
        result = 31 * result + Arrays.hashCode(likes);
        result = 31 * result + Arrays.hashCode(dislikes);
        return result;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", creationTime='" + creationTime + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", user=" + user +
                ", comment=" + Arrays.toString(comment) +
                ", likes=" + Arrays.toString(likes) +
                ", dislikes=" + Arrays.toString(dislikes) +
                ", optional='" + optional + '\'' +
                '}';
    }
}
