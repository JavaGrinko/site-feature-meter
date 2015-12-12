package javagrinko.sitefeaturemeter.services;

import javagrinko.sitefeaturemeter.dom.User;
import javagrinko.sitefeaturemeter.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUser() {
        List<User> all = userRepository.findAll();
        if (all.size() > 0) {
            return all.get(0);
        } else {
            return null;
        }
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
