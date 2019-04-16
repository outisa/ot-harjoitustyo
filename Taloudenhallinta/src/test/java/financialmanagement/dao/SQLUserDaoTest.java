
package financialmanagement.dao;

import financialmanagement.domain.User;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
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
public class SQLUserDaoTest {
    
    private UserDao dao;
    private Properties properties;
    private String testDatabase;
    
    @Before
    public void setUp() throws SQLException, IOException {
        properties = new Properties();
        InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties");
        properties.load(is);
       
        testDatabase = properties.getProperty("testString");
        dao = new SQLUserDao(testDatabase);
        String sqlAddUser = "INSERT INTO Account (username) VALUES (?)";
        Connection connection = DriverManager.getConnection(testDatabase); 
        PreparedStatement stmt = connection.prepareStatement(sqlAddUser);
        stmt.setString(1, "tester");
        stmt.executeUpdate();
        stmt.close();
        connection.close();
        
    }
    @Test
    public void userCanBeCreated() throws Exception {
        User user = new User("another");
        dao.create(user);
        User userTest = dao.findByUsername("another");
        assertEquals("another", userTest.getUsername());
    }
    @Test
    public void userIsFoundByUsername() throws Exception {
        User user = dao.findByUsername("tester");
        assertEquals("tester", user.getUsername());
    }
    
    @Test
    public void nonExistingUserIsFoundByUsename() throws Exception {
        User user = dao.findByUsername("Another");
        assertEquals(null, user);
    }
    
    @Test
    public void usersAreListedCorrectly() throws Exception {
        List<User> users = dao.getAll();
        assertEquals(1, users.size());
    }

    @After
    public void tearDown() throws SQLException {
        String sql = "DROP TABLE Account";
        Connection connection = DriverManager.getConnection(testDatabase); 
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
        stmt.close();
        connection.close();

    }
}
