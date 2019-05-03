
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class SQLExpenseDaoTest {
    
    private ExpenseDao dao;
    private UserDao userDao;
    private String testDatabase;
    
    @Before
    public void setUp() throws IOException, SQLException, Exception {
        Properties properties = new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties");
        properties.load(is);
       
        testDatabase = properties.getProperty("testString");
        dao = new SQLExpenseDao(testDatabase);
        userDao = new SQLUserDao(testDatabase);
        String sqlAddUser = "INSERT INTO Account (username) VALUES (?)";
        String sqlAddExpense = "INSERT INTO Expense (account_id, date, category, amount) VALUES (?, ?, ?, ?)";
        Connection connection = DriverManager.getConnection(testDatabase); 
        PreparedStatement stmt = connection.prepareStatement(sqlAddExpense);
        stmt.setInt(1, 1);
        stmt.setDate(2, Date.valueOf("2019-11-23"));
        stmt.setString(3, "Food");
        stmt.setDouble(4, 59.0);
        stmt.executeUpdate();
        stmt = connection.prepareStatement(sqlAddUser);
        stmt.setInt(1, 1);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
    
    @After
    public void tearDown() throws SQLException {
        String sql = "DROP TABLE Expense";
        String sql2 = "DROP TABLE Account";
        Connection connection = DriverManager.getConnection(testDatabase); 
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
        stmt.execute(sql2);
        stmt.close();
        connection.close();
    }

    @Test
    public void expenseCanBeCreated() throws Exception {
        Date date = Date.valueOf("2019-08-01");
        Integer id = 1;
        Expense expense = new Expense(id, date, "Car", 140.0);
        dao.create(expense);
        Expense expenseTest = dao.findExpense(date, 140.0, "Car", id);
        assertTrue(expense.equals(expenseTest));
    }
    
    @Test
    public void expenseIsFound() throws Exception {
        Date date = Date.valueOf("2019-11-23");
        Integer id = 1;
        Expense expense = dao.findExpense(date, 59.0, "Food", id);
        assertTrue(expense.getUserId().equals(1));
        assertEquals("Food", expense.getCategory());
        assertEquals(date, expense.getDate());
        assertEquals(59.0, expense.getAmount(), 0.00);
    }
    
    @Test
    public void nonExistingExpenseIsFound() throws Exception {
        Integer id = 3;
        Expense expense = dao.findExpense(Date.valueOf("2019-01-01"), 12.0, "Hobbies", id);
        assertEquals(null, expense);
    }
    
    @Test
    public void tenResentExpensesAreFoundLimitCorrect() throws Exception {
        Integer id = 1;
        for (int i = 0; i < 12; i++) {
            Double amount = 12.0 + i;
            dao.create(new Expense(id, Date.valueOf("2019-01-01"), "Food", amount));
        }
        List<Expense> expenses = dao.getTenResentlyAdded(1);
        assertEquals(10, expenses.size());
    }
    
    @Test
    public void tenResentExpensesAreFoundForCurrentUser() throws Exception {
        Integer id = 1;
        for (int i = 0; i < 12; i++) {
            Double amount = 12.0 + i;
            dao.create(new Expense(2, Date.valueOf("2019-01-01"), "Other", amount));
        }
        List<Expense> incomes = dao.getTenResentlyAdded(id);
        assertEquals(1, incomes.size());
    }
    
    @Test
    public void expensesPerCategoryIsCorrect() throws Exception {
        Integer id = 1;
        for (int i = 0; i < 3; i++) {
            Double amount = 100.0 + i;
            dao.create(new Expense(id, Date.valueOf("2019-01-01"), "Hobbies", amount));
        }
        for (int i = 0; i < 3; i++) {
            Double amount = 99.0 + i;
            dao.create(new Expense(id, Date.valueOf("2019-11-11"), "Education", amount));
        }
        Integer id2 = 2;
        dao.create(new Expense(id2, Date.valueOf("2019-11-11"), "Hobbies", 13.00));
                
        HashMap<String, ArrayList<Double>> categories = dao.expenseForEachCategory(id);
        assertEquals(303, categories.get("Hobbies").get(0), 0.001);       
    }
    
    @Test
    public void expensesBetweenReturnsRight() throws Exception {
        Integer id = 1;
        for (int i = 0; i < 2; i++) {
            Double amount = 100.0 + i;
            dao.create(new Expense(id, Date.valueOf("2019-11-30"), "Hobbies", amount));
        }
        Integer id2 = 2;
        dao.create(new Expense(id2, Date.valueOf("2019-11-11"), "Hobbies", 13.00));
        dao.create(new Expense(id, Date.valueOf("2019-08-11"), "Food", 123.00));
        assertEquals(3, dao.getAllBetween(Date.valueOf("2019-11-01"), Date.valueOf("2020-01-01"), id).size());
        
        
    }
}
