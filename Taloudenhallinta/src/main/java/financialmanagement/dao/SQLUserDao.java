package financialmanagement.dao;

import financialmanagement.domain.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This class communicates with database with user related data.
 */
public class SQLUserDao implements UserDao {
    private String database;

    /**
     * Constructor creates Account table, if not already exists.
     * 
     * @param database database, which will be used in the methods of this class 
     * @throws java.sql.SQLException 
     */    
    public SQLUserDao(String database) throws SQLException {
        this.database = database;
        createTableIfNotExist();
    }
    
    /*
    * Connects to the database.
    * @return the Connection object
    */
    private Connection connect() throws SQLException {
        Connection connection = DriverManager.getConnection(database);
        return connection;
    }
    
    /**
     * Inserts a new User to the table Account.
     * 
     * @param user user, which will be inserted to database
     * @return user
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
     * Searches from table Account with given username whether user does exist.
     * 
     * @param username username from the user
     * @return user, if user already exists and otherwise null 
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
    
    /*
     * Creates a table Account in the management database,
     * if it does not already exists. 
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
