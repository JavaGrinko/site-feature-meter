package javagrinko.sitefeaturemeter.services;

import javagrinko.sitefeaturemeter.dom.User;

public interface UserService {
    User getUser();
    void saveUser(User user);
}
