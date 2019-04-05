package financialmanagement.dao;

import financialmanagement.domain.User;
import java.util.ArrayList;
import java.util.List;


public class SQLUserDao implements UserDao {
    private List<User> users;

    public SQLUserDao() {
        users = new ArrayList<>();
    }

    @Override
    public User create(User user) throws Exception {
        users.add(user);
        return user;
    }

    @Override
    public User findByUsername(String username) {
        for (User user: users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getAll() {
        return users;
    }
}
