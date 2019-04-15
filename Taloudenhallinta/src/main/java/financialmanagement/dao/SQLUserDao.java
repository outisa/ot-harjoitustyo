package financialmanagement.dao;

import financialmanagement.domain.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class communicates with database with user related data.
 * @author outisa
 */
public class SQLUserDao implements UserDao {
    private String database;

    public SQLUserDao(String database) throws SQLException {
        this.database = database;
        createTableIfNotExist();
    }
    
    /**
    * Connect to the database
    *
    * @return the Connection object
    */
    private Connection connect() throws SQLException {
        Connection connection = null;
        connection = DriverManager.getConnection(database);
        return connection;
    }
    
    /**
     * Insert a new User to the table Account 
     * 
     * @param user
     * @return Current user of this application
     * @throws Exception 
     */
    @Override
    public User create(User user) throws Exception {
        String sql = "INSERT INTO Account (username) VALUES (?)";       
        Connection connection = this.connect();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, user.getUsername());
        stmt.executeUpdate();
        stmt.close();
        connection.close();
        return user;
    }
  
    /**
     * Search from table Account with given username whether user does exist.
     * 
     * @param username
     * @return user, if already exists; null, if no user exists with given username 
     * @throws java.sql.SQLException 
     */
    @Override
    public User findByUsername(String username) throws SQLException {
        ArrayList<User> users = new ArrayList<>();     
        String sql = "SELECT * FROM Account WHERE username = ?";
        Connection connection = this.connect(); 
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            users.add(new User(rs.getInt("id"), rs.getString("username")));
        }
        rs.close();
        stmt.close();
        connection.close();
        
        if (users.isEmpty()) {
            return null;
        }
        return users.get(0);
    }
    
    /**
     * List all users from the database.
     * @return users , list of all users in the database. 
     */
    @Override
    public List<User> getAll() throws SQLException {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Account";
        Connection connection = this.connect();
        PreparedStatement stmt = connection.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            users.add(new User(rs.getInt("id"), rs.getString("username")));
        }
        stmt.close();
        rs.close();
        connection.close();

        return users;
    }
   
    /**
     * Create a table Account in the management database,
     * if it does not already exists.
     * 
     * @throws SQLException 
     */
    private void createTableIfNotExist() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Account ("
                + " id integer PRIMARY KEY AUTOINCREMENT,"
                + " username VARCHAR(100) NOT NULL"
                + ");";
        try (Connection connection = DriverManager.getConnection(database); Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            stmt.close();
            connection.close();
        }
    }
}
