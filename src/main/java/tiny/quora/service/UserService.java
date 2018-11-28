package tiny.quora.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tiny.quora.dao.UserDao;
import tiny.quora.model.User;


@Service
public class UserService {
    @Autowired
    UserDao userDao;

    public User getUser(int userId) {
        User user = userDao.searchById(userId);
        user.setId(userId);
        return user;
    }

    public User getUser(String name) {
        User user = userDao.searchByName(name);
        user.setName(name);
        return user;
    }

}
