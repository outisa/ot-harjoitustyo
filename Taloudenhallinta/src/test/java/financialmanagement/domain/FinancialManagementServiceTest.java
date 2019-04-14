
package financialmanagement.domain;

import java.sql.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class FinancialManagementServiceTest {
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
    
    @Test
    public void incomeCanBeCreated() throws Exception {
        Date date = Date.valueOf("2019-12-01");
        assertTrue(service.createIncome(date, 12.90, "Other", 1));
        
    }
    @Test 
    public void incomeCanNotBeCreatedIfAlreadyExists() throws Exception {
        Date date = Date.valueOf("2019-12-24");
        assertFalse(service.createIncome(date, 120.00, "Present", 1));
        
    }
    
    @Test
    public void expenseCanBeCreated() throws Exception {
        Date date = Date.valueOf("2019-05-11");
        assertTrue(service.createExpense(date, 128.90, "Car", 1));
        
    }
    @Test 
    public void expenseCanNotBeCreatedIfAlreadyExists() throws Exception {
        Date date = Date.valueOf("2019-07-26");
        assertFalse(service.createExpense(date, 12.05, "Food", 1));
        
    }
    @Test
    public void expenseListIsReturned() throws Exception {
        assertEquals(1,service.listExpenses(1).size());
    }
    
    @Test
    public void incomeListIsReturned() throws Exception {
        assertEquals(1, service.listIncomes(1).size());
    }
        
}
