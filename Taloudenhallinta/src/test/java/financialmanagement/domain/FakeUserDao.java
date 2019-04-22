package financialmanagement.domain;

import financialmanagement.dao.UserDao;
import java.util.ArrayList;
import java.util.List;


public class FakeUserDao implements UserDao {
    List<User> users;

    public FakeUserDao() {
        users = new ArrayList<>();
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

}
