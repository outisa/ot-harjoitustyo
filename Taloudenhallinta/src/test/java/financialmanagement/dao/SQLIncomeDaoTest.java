
package financialmanagement.dao;

import financialmanagement.domain.Income;
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

/**
 * This class is for testing class SQLIncomeDao
 * @author ousavola
 */
public class SQLIncomeDaoTest {
    
    private IncomeDao dao;
    private UserDao userDao;
    private String testDatabase;
    
    @Before
    public void setUp() throws IOException, SQLException {
        Properties properties = new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties");
        properties.load(is);
       
        testDatabase = properties.getProperty("testString");
        dao = new SQLIncomeDao(testDatabase);
        userDao = new SQLUserDao(testDatabase);
        String sqlAddUser = "INSERT INTO Account (username) VALUES (?)";
        String sqlAddIncome = "INSERT INTO Income (account_id, date, category, amount) VALUES (?, ?, ?, ?)";
        Connection connection = DriverManager.getConnection(testDatabase); 
        PreparedStatement stmt = connection.prepareStatement(sqlAddIncome);
        stmt.setInt(1, 1);
        stmt.setDate(2, Date.valueOf("2019-01-01"));
        stmt.setString(3, "Salary");
        stmt.setDouble(4, 3459.0);
        stmt.executeUpdate();
        stmt = connection.prepareStatement(sqlAddUser);
        stmt.setString(1, "user");
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
    
    @After
    public void tearDown() throws SQLException {
        String sql = "DROP TABLE Income";
        String sql2 = "DROP TABLE Account";        
        Connection connection = DriverManager.getConnection(testDatabase); 
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
        stmt.execute(sql2);
        stmt.close();
        connection.close();
    }

    @Test
    public void incomeCanBeCreated() throws Exception {
        Date date = Date.valueOf("2019-08-01");
        Income income = new Income(1, date, "Present", 100.0);
        dao.create(income);
        Income incomeTest = dao.findIncome(date, 100.0, "Present", 1);
        assertTrue(income.equals(incomeTest));
    }
    
    @Test
    public void incomeIsFound() throws Exception {
        Date date = Date.valueOf("2019-01-01");
        Income income = dao.findIncome(date, 3459.0, "Salary", 1);
        assertTrue(income.getUserId().equals(1));
        assertEquals("Salary", income.getCategory());
        assertEquals(date, income.getDatetime());
        assertEquals(3459.0, income.getAmount(), 0.00);
    }
    
    @Test
    public void nonExistingIncomeIsFound() throws Exception {
        Income income = dao.findIncome(Date.valueOf("2019-01-01"), 12.0, "Present", 3);
        assertEquals(null, income);
    }
    
    @Test
    public void tenResentIncomesAreFoundForCurrentUser() throws Exception {
        Integer id = 1;
        for (int i = 0; i < 12; i++) {
            Double amount = 12.0 + i;
            dao.create(new Income(2, Date.valueOf("2019-01-01"), "Present", amount));
        }
        List<Income> incomes = dao.getTenResentAdded(id);
        assertEquals(1, incomes.size());
    }
    
    @Test
    public void tenResentIncomesAreFoundSizeCorrect() throws Exception {
        Integer id = 1;
        for (int i = 0; i < 12; i++) {
            Double amount = 12.0 + i;
            dao.create(new Income(id, Date.valueOf("2019-01-01"), "Present", amount));
        }
        List<Income> incomes = dao.getTenResentAdded(id);
        assertEquals(10, incomes.size());
    }
    @Test
    public void incomesPerCategoryIsCorrect() throws Exception {
        Integer id = 1;
        for (int i = 0; i < 3; i++) {
            Double amount = 12.0 + i;
            dao.create(new Income(id, Date.valueOf("2019-01-01"), "Present", amount));
        }
        for (int i = 0; i < 3; i++) {
            Double amount = 1000.0 + i;
            dao.create(new Income(id, Date.valueOf("2019-01-11"), "Salary", amount));
        }
        Integer id2 = 2;
        dao.create(new Income(id2, Date.valueOf("2019-11-01"), "Present", 13.00));
                
        HashMap<String, ArrayList<Double>> categories = dao.incomeForEachCategory(id);
        assertEquals(39, categories.get("Present").get(0), 0.001);       
    }

}
