/*
 * This class represents a singular user
 */
package financialmanagement.domain;

public class User {
    private Integer id;
    private String username;

    public User(Integer id, String username) {
        this.username = username;
        this.id = id;
    }
    public User(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }
    
    public String getUsername() {
        return username;
    }
    /**
     * Investigates whether given object equals with User or not.
     * 
     * @param object 
     * @return true, if given object equals with User; false, if given object is not instance of User
     * or it does not equals with User.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof User)) {
            return false;
        }
        
        User other = (User) object;
        return username.equals(other.username);
    }
    
    
}
