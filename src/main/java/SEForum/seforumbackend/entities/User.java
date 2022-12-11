package SEForum.seforumbackend.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Arrays;
import java.util.Objects;

@Document(collection = "user")
@Data
@NoArgsConstructor
public class User {

    @Id
    public String id;
    @Field
    public String username;
    @Field
    public String passwordHash;
    @Field
    public String optional;

    public User(String id, String username, String passwordHash, String... optional) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.optional = optional != null ? Arrays.toString(optional) : "";
    }
}
