package financialmanagement.domain;

import financialmanagement.dao.UserDao;

/*
 * This class  
 */

public class FinancialManagementService {
    private User loggedIn;
    private UserDao userDao;

    public FinancialManagementService(UserDao userDao) {
        this.userDao = userDao;
    }
    
    // All the User related functions below.
    public boolean createUser(String username) {
        if (userDao.findByUsername(username) != null) {
            return false;
        }
        User user = new User(username);
        try {
            userDao.create(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    //login function
    public boolean login(String username) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            return false;
        }
        loggedIn = user;
        return true;
    }
    
    // returns user, which is logged in.
    public User getLoggedUser() {
        return loggedIn;
    }
    
    // Logout function
    public void logout() {
        loggedIn = null;
    }
}
