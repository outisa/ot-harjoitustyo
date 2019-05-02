

package financialmanagement.domain;

import java.sql.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests all Expense related methods from FinancialManagementSercvice
 */
public class FinancialManagementServiceExpenseTest {
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
    public void expensesBetweenReturnsCorrect() {
        assertEquals(5, service.listExpensesBetween(1, Date.valueOf("2019-05-01"), Date.valueOf("2019-08-01")).size());
    }
    
    // It would be better test perhaps more values.
    @Test
    public void expensesPerCategoryDataIsCorrect() throws Exception {
        assertEquals(300.00, service.overviewExpenses(1).get("Food").get(0), 0.001);
    }
}
