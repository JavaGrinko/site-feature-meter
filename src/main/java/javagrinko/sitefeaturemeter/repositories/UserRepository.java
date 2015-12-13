package javagrinko.sitefeaturemeter.repositories;

import javagrinko.sitefeaturemeter.dom.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
}
