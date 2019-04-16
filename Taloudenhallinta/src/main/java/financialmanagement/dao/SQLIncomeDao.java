package financialmanagement.dao;

import financialmanagement.domain.Income;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class communicates with database with income related data.
 * @author ousavola
 */
public class SQLIncomeDao implements IncomeDao {
    private String database;

    public SQLIncomeDao(String database) {
        this.database = database;
        createIncomeTable();
    }
    /**
     * Creates the connection with the database.
     * 
     * @return connection to the database.
     */
    private Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(database);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return connection;
    }

    /**
     * Inserts the given income to the database.
     * @param income
     * @throws Exception 
     */
    @Override
    public void create(Income income) throws Exception {
        String sql = "INSERT INTO Income(account_id, date, category, amount) VALUES (?, ?, ?, ?)";
        try (Connection connection = this.connect()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, income.getUserId());
            stmt.setDate(2, income.getDatetime());
            stmt.setString(3, income.getCategory());
            stmt.setDouble(4, income.getAmount());
            stmt.executeUpdate();
            stmt.close();
            connection.close();
        }
    }
    
    /**
     * Creates HashMap, which keys are categories and values are total amount of received income per category.
     * 
     * @return 
     */
    @Override
    public HashMap<String, Integer> incomeForEachCategory() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Controls, if there is an existing income to the given data
     * @param date
     * @param amount
     * @param category
     * @param userId
     * @return null, if no income was found; income, if it was found
     */
    @Override
    public Income findIncome(Date date, Double amount, String category, Integer userId) {
        List<Income> incomes = new ArrayList<>();
        Income newIncome = new Income(userId, date, category, amount);
        String sql = "SELECT * FROM Income";
        try (Connection connection = this.connect(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                incomes.add(new Income(rs.getInt("account_id"), rs.getDate("date"), rs.getString("category"), rs.getDouble("amount")));
            }
            closeConnection(stmt, rs, connection);
        } catch (SQLException ex) {
            Logger.getLogger(SQLIncomeDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Income income: incomes) {
            if (income.equals(newIncome)) {
                return income;                
            }
        }
        return null;
    }

    /**
     * Search ten by date newest income from database for current user.
     * @param userId
     * @return list of incomes
     */
    @Override
    public List<Income> getTenResentAdded(Integer userId)  {
        List<Income> incomesForCurrentUser = new ArrayList<>();
        String sql = "SELECT * FROM Income WHERE account_id = ? ORDER BY date DESC LIMIT 10";
        try (Connection connection = this.connect()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                incomesForCurrentUser.add(new Income(rs.getInt("account_id"), rs.getDate("date"), rs.getString("category"), rs.getDouble("amount")));
            }            
            closeConnection(stmt, rs, connection);
        } catch (SQLException ex) {
            Logger.getLogger(SQLIncomeDao.class.getName()).log(Level.SEVERE, null, ex);           
        }
        return incomesForCurrentUser;
    }
    /**
     * Close connection for given parameters.
     * @param stmt
     * @param rs
     * @param connection
     * @throws SQLException 
     */
    private void closeConnection(PreparedStatement stmt, ResultSet rs, Connection connection) throws SQLException {
        stmt.close();
        rs.close();
        connection.close();
    }
    
     /**
     * Creates Income table in the database, if not already exists.
     */
    private void createIncomeTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Income("
                + " id integer PRIMARY KEY AUTOINCREMENT,"
                + " account_id INTEGER,"
                + " date DATE," 
                + " category VARCHAR(100) NOT NULL,"
                + " amount NUMERIC(10,2),"
                + " FOREIGN KEY (account_id) REFERENCES Account(id)"
                + ");";
        try (Connection connection = DriverManager.getConnection(database); Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            Logger.getLogger(SQLIncomeDao.class.getName()).log(Level.SEVERE, null, e);           
        }
    }
    
}
