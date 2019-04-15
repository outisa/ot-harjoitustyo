
package financialmanagement.dao;

import financialmanagement.domain.User;
import java.util.List;

public interface UserDao {
    User create(User user) throws Exception;
    
    User findByUsername(String username) throws Exception;
    
    List<User> getAll() throws Exception;
}
