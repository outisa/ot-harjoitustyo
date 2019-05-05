package financialmanagement.dao;

import financialmanagement.domain.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * This class communicates with database with user related data.
 */
public class SQLUserDao implements UserDao {
    private DatabaseConnector connector;

    /**
     * Constructor creates Account table, if not already exists.
     * 
     * @param database database, in which the data is stored.
     * @throws java.sql.SQLException 
     */    
    public SQLUserDao(String database) throws SQLException {
        connector = new DatabaseConnector(database);
        createTableIfNotExist();
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
        Connection connection = connector.connect();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, user.getUsername());
        stmt.executeUpdate();
        connector.closeConnection(stmt, connection);
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
        Connection connection = connector.connect(); 
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            users.add(new User(rs.getInt("id"), rs.getString("username")));
        }
        
        connector.closeConnectionWithResultSet(stmt, rs, connection);
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
        try (Connection connection = connector.connect(); Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            stmt.close();
            connection.close();
        }
    }
}
