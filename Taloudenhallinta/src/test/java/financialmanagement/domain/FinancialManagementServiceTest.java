
package financialmanagement.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public void userCanBeCreated() {
        assertTrue(service.createUser("Another"));
    }
    @Test
    public void existingUserCanNotBeCreated() {
        assertFalse(service.createUser("Tester"));
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
    public void loggedUserCanLogOut() {
        service.login("Tester");
        service.logout();
        assertEquals(null, service.getLoggedUser());
    }
    
    @Test
    public void incomeCanBeCreated() {
        LocalDateTime date = LocalDateTime.parse("2019-12-01"+ " " + "00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertTrue(service.createIncome(date, 12.90, "Other", 1));
        
    }
    @Test 
    public void incomeCanNotBeCreatedIfAlreadyExists() {
        LocalDateTime date = LocalDateTime.parse("2019-12-24"+ " " + "00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertFalse(service.createIncome(date, 120.00, "Present", 1));
        
    }
    
    @Test
    public void expenseCanBeCreated() {
        LocalDateTime date = LocalDateTime.parse("2019-05-11"+ " " + "00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertTrue(service.createExpense(date, 128.90, "Car", 1));
        
    }
    @Test 
    public void expenseCanNotBeCreatedIfAlreadyExists() {
        LocalDateTime date = LocalDateTime.parse("2019-07-26"+ " " + "00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        assertFalse(service.createExpense(date, 12.05, "Food", 1));
        
    }
    @Test
    public void expenseListIsReturned() {
        assertEquals(1,service.listExpenses(1).size());
    }
    
    @Test
    public void incomeListIsReturned() {
        assertEquals(1, service.listIncomes(1).size());
    }
        
}
