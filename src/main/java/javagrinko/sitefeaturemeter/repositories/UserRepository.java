package javagrinko.sitefeaturemeter.repositories;

import javagrinko.sitefeaturemeter.dom.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, Long> {
}
