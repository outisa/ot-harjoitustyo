package financialmanagement.domain;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests all user related methods from FinancialManagementService.
 */
public class FinancialManagementServiceUserTest {
    FakeUserDao userDao;
    FakeIncomeDao incomeDao;
    FakeExpenseDao expenseDao;
    FinancialManagementService service;

    @Before
    public void setUp() {
        userDao = new FakeUserDao();
        incomeDao = new FakeIncomeDao();
        expenseDao = new FakeExpenseDao();
        service = new FinancialManagementService(userDao, incomeDao, expenseDao);
    }
    
    @Test
    public void userCanBeCreated() throws Exception {
        assertTrue(service.createUser("Another"));
    }
    
    @Test
    public void existingUserCanNotBeCreated() throws Exception {
        assertFalse(service.createUser("Tester"));
    }

    @Test
    public void nonExistingUserCanNotLogIn() throws Exception {
        boolean result = service.login("notAUser");
        assertFalse(result);
        
        assertEquals(null, service.getLoggedUser());
    }
    
    @Test
    public void existingUserCanLoginIn() throws Exception {
        boolean result = service.login("Tester");
        assertTrue(result);
        
        User loggedIn = service.getLoggedUser();
        assertEquals("Tester", loggedIn.getUsername());
    }
    @Test
    public void loggedUserCanLogOut() throws Exception {
        service.login("Tester");
        service.logout();
        assertEquals(null, service.getLoggedUser());
    }
      

}
