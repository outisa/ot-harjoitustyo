package financialmanagement.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class UserTest {
    User user1;
    User user2;
    User user3;
               
    @Before
    public void setUp() {
        this.user1 = new User("Tester");
        this.user2 = new User("Another");
        this.user3 = new User("Tester");
    }
    
    @Test
    public void userDoesExist() {
        assertTrue(user1.getUsername().equals("Tester"));
    }
    
    @Test
    public void usersAreEqualWithSameUsername() {
       assertTrue(user1.equals(user3));
    }
    
    @Test 
    public void usersAreNonequalWhenUsenameNotSame() {
        assertFalse(user1.equals(user2));
    }
    
    @Test
    public void usersAreNonequalWithDiffenrentType() {
       Object obj = new Object();
       assertFalse(user1.equals(obj));
    }
}    
