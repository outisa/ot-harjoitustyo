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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) {
            return false;
        }
        
        User other = (User) o;
        return username.equals(other.username);
    }
    
    
}
