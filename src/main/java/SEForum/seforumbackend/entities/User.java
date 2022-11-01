package SEForum.seforumbackend.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Arrays;
import java.util.Objects;

@Document(collection = "user")
@Getter
@Setter
public class User {

    @Id
    public int id;
    @Field
    public String username;
    @Field
    public String passwordHash;
    @Field
    public String optional;

    public User() {
    }

    public User(String username, String passwordHash, String... optional) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.optional = optional != null ? Arrays.toString(optional) : "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return username.equals(user.username) && passwordHash.equals(user.passwordHash) && Objects.equals(optional, user.optional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, passwordHash, optional);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", optional='" + optional + '\'' +
                '}';
    }
}
