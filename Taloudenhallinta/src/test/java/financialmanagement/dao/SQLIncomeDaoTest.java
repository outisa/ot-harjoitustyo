
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
    private String testDatabase;
    
    @Before
    public void setUp() throws IOException, SQLException {
        Properties properties = new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties");
        properties.load(is);
       
        testDatabase = properties.getProperty("testString");
        dao = new SQLIncomeDao(testDatabase);
        String sqlAddIncome = "INSERT INTO Income (account_id, date, category, amount) VALUES (?, ?, ?, ?)";
        Connection connection = DriverManager.getConnection(testDatabase); 
        PreparedStatement stmt = connection.prepareStatement(sqlAddIncome);
        stmt.setInt(1, 1);
        stmt.setDate(2, Date.valueOf("2019-01-01"));
        stmt.setString(3, "Salary");
        stmt.setDouble(4, 3459.0);
        stmt.executeUpdate();
        stmt.close();
        connection.close();
    }
    
    @After
    public void tearDown() throws SQLException {
        String sql = "DROP TABLE Income";
        Connection connection = DriverManager.getConnection(testDatabase); 
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
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
    public void tenResentIncomesAreFound() throws Exception {
        for (int i = 0; i < 12; i++) {
            Double amount = 12.0 + i;
            dao.create(new Income(1, Date.valueOf("2019-01-01"), "Present", amount));
        }
        List<Income> incomes = dao.getTenResentAdded(1);
        assertEquals(10, incomes.size());
    }
}
