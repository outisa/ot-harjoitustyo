
package financialmanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Connects and disconnects to the database.
 */
public class DatabaseConnector {
    String database;

    public DatabaseConnector(String database) {
        this.database = database;
    }
    
    /**
    * Connects to the database.
    * @return connection to the database. 
    */
    public Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(database);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }
    
    /**
     * Closes connection to the database.
     * @param stmt PreparedStatement 
     * @param rs ResultSet
     * @param connection Connection 
     * @throws SQLException if an error occurs by closing the connection.
     */
    public void closeConnection(PreparedStatement stmt, ResultSet rs, Connection connection) throws SQLException {
        stmt.close();
        rs.close();
        connection.close();
    }
    
    /**
     * Closes connection to the database, difference to closeConnection() is, that this method
     * do not close ResulSet.
     * @param stmt PreparedStatement
     * @param connection Connection
     * @throws SQLException if an error occurs by closing the connection.
     */
    public void closeConnectionShort(PreparedStatement stmt, Connection connection) throws SQLException {
        stmt.close();
        connection.close();
    }
    
    
}
