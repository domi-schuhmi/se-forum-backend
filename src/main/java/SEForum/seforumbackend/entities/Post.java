package SEForum.seforumbackend.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
@Getter
@Setter
public class Post {

    @Id
    public String id;
    @Field
    public String username;
    @Field
    public String passwordHash;
    @Field
    public String optional;

    public Post() {
    }

}
