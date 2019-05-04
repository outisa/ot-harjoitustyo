
package financialmanagement.dao;

import financialmanagement.domain.User;

/**
 * Interface for SQLUserDao.
 */
public interface UserDao {
    /**
     * Creates a new user.
     * @param user users username
     * @return user
     * @throws Exception 
     */
    User create(User user) throws Exception;
    
    /**
     * Search if there exists a user with given username.
     * @param username users username
     * @return user if user exists, otherwise null
     * @throws Exception 
     */
    User findByUsername(String username) throws Exception;
}
