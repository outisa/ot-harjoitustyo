
package financialmanagement.dao;

import financialmanagement.domain.Expense;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ousavola
 */
public class SQLExpenseDaoTest {
    
    private ExpenseDao dao;
    private String testDatabase;
    
    @Before
    public void setUp() throws IOException, SQLException, Exception {
        Properties properties = new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties");
        properties.load(is);
       
        testDatabase = properties.getProperty("testString");
        dao = new SQLExpenseDao(testDatabase);
        String sqlAddExpense = "INSERT INTO Expense (account_id, date, category, amount) VALUES (?, ?, ?, ?)";
        Connection connection = DriverManager.getConnection(testDatabase); 
        PreparedStatement stmt = connection.prepareStatement(sqlAddExpense);
        stmt.setInt(1, 1);
        stmt.setDate(2, Date.valueOf("2019-11-23"));
        stmt.setString(3, "Food");
        stmt.setDouble(4, 59.0);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
    
    @After
    public void tearDown() throws SQLException {
        String sql = "DROP TABLE Expense";
        Connection connection = DriverManager.getConnection(testDatabase); 
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
        stmt.close();
        connection.close();
    }

    @Test
    public void expenseCanBeCreated() throws Exception {
        Date date = Date.valueOf("2019-08-01");
        Expense expense = new Expense(1, date, "Car", 140.0);
        dao.create(expense);
        Expense expenseTest = dao.findExpense(date, 140.0, "Car", 1);
        assertTrue(expense.equals(expenseTest));
    }
    
    @Test
    public void expenseIsFound() throws Exception {
        Date date = Date.valueOf("2019-11-23");
        Expense expense = dao.findExpense(date, 59.0, "Food", 1);
        assertTrue(expense.getUserId().equals(1));
        assertEquals("Food", expense.getCategory());
        assertEquals(date, expense.getDate());
        assertEquals(59.0, expense.getAmount(), 0.00);
    }
    
    @Test
    public void nonExistingExpenseIsFound() throws Exception {
        Expense expense = dao.findExpense(Date.valueOf("2019-01-01"), 12.0, "Hobbies", 3);
        assertEquals(null, expense);
    }
    
    @Test
    public void tenResentExpensesAreFound() throws Exception {
        for (int i = 0; i < 12; i++) {
            Double amount = 12.0 + i;
            dao.create(new Expense(1, Date.valueOf("2019-01-01"), "Food", amount));
        }
        List<Expense> expenses = dao.getTenResentlyAdded(1);
        assertEquals(10, expenses.size());
    }
}
