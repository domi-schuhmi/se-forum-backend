package SEForum.seforumbackend.controller;

import SEForum.seforumbackend.entities.User;
import SEForum.seforumbackend.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController()
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserRepository userrepo;
    private final Logger logger;


    @Autowired
    public UserController(UserRepository userrepo) {
        this.userrepo = userrepo;
        this.logger = (Logger) LoggerFactory.getLogger(UserController.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(UserController.class, args);
    }

    @GetMapping("/all")
    public List<User> getList() {
        List<User> result = userrepo.findAll();
        for (User user : result) {
            this.logger.info(user.toString());
        }
        return result;
    }

    @PostMapping("/add")
    public User getList(@RequestBody User newUser) {
        if (newUser == null || newUser.username == null || newUser.passwordHash == null) {
            throw new IllegalArgumentException();
        }
        this.logger.info(newUser.toString());
        if (newUser.id != null) {
            Optional<User> c = userrepo.findById(newUser.id);
            if (c.isPresent()) {
                return c.get();
            } else {
                userrepo.save(newUser);
                return newUser;
            }
        } else {
            userrepo.save(newUser);
            return newUser;
        }
    }

}