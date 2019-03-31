package financialmanagement.domain;

import financialmanagement.dao.UserDao;
import java.util.ArrayList;
import java.util.List;


public class FakeUserDao implements UserDao {
    List<User> users = new ArrayList<>();

    public FakeUserDao() {
        users.add(new User("Tester"));
    }

    
    @Override
    public User create(User user) throws Exception {
        users.add(user);
        return user;
    }

    @Override
    public User findByUsername(String username) {
       for(User user: users){
           if(user.getUsername().equals(username)){
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
