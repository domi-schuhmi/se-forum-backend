package SEForum.seforumbackend.controller;

import SEForum.seforumbackend.entities.User;
import SEForum.seforumbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
@CrossOrigin(origins = "*")
public class UserController implements CommandLineRunner{

    private final UserRepository userrepo;


    @Autowired
    public UserController(UserRepository userrepo) {
        this.userrepo = userrepo;
    }

    public static void main(String[] args) {
        SpringApplication.run(UserController.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        userrepo.deleteAll();

        // save a couple of customers
        userrepo.save(new User("Alice", "Smith"));
        userrepo.save(new User("Bob", "Smith"));

        // fetch all customers
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (User customer : userrepo.findAll()) {
            System.out.println(customer);
        }
        System.out.println();

        // fetch an individual customer
        System.out.println("Customer found with findByFirstName('Alice'):");
        System.out.println("--------------------------------");
        System.out.println(userrepo.findByFirstName("Alice"));

        System.out.println("Customers found with findByLastName('Smith'):");
        System.out.println("--------------------------------");
        for (User customer : userrepo.findByLastName("Smith")) {
            System.out.println(customer);
        }

    }

}