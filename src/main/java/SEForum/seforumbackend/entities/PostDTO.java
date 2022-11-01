package SEForum.seforumbackend.entities;

import java.util.Arrays;
import java.util.Objects;


public class PostDTO {

    public int id;
    public String creationTime;
    public String title;
    public String content;
    public int user;
    public Comment[] comment;
    public Like[] likes;
    public Dislike[] dislikes;
    public String optional;

    public PostDTO() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PostDTO postDTO = (PostDTO) o;
        return user == postDTO.user && creationTime.equals(postDTO.creationTime) && title.equals(postDTO.title) && content.equals(postDTO.content) && Arrays.equals(comment, postDTO.comment) && Arrays.equals(likes, postDTO.likes) && Arrays.equals(dislikes, postDTO.dislikes) && Objects.equals(optional, postDTO.optional);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(creationTime, title, content, user, optional);
        result = 31 * result + Arrays.hashCode(comment);
        result = 31 * result + Arrays.hashCode(likes);
        result = 31 * result + Arrays.hashCode(dislikes);
        return result;
    }

    @Override
    public String toString() {
        return "PostDTO{" +
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
