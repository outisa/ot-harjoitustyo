
package financialmanagement.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class FinancialManagementServiceTest {
    FakeUserDao userDao;
    FinancialManagementService service;

    @Before
    public void setUp() {
        userDao = new FakeUserDao();
        service = new FinancialManagementService(userDao);
    }
    

    @Test
    public void nonExistingUserCanNotLogIn() {
        boolean result = service.login("notAUser");
        assertFalse(result);
        
        assertEquals(null, service.getLoggedUser());
    }
    
    @Test
    public void existingUserCanLoginIn() {
        boolean result = service.login("Tester");
        assertTrue(result);
        
        User loggedIn = service.getLoggedUser();
        assertEquals("Tester", loggedIn.getUsername());
    }
    @Test
    public void loggedUserCanLogOut(){
        service.login("Tester");
        service.logout();
        assertEquals(null, service.getLoggedUser());
    }
}
