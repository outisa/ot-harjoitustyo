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

    /**
     * Constructor creates Income table, if not already exists.
     * @param database database, which will be used in the methods of this class 
     * @throws java.sql.SQLException 
     */
    public SQLIncomeDao(String database) throws SQLException {
        this.database = database;
        createIncomeTable();
    }

    // Creates connection to the given database
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
     * @param income income, which will be inserted into database
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
     * Creates HashMap, which keys are categories and values are another HashMaps
     * with total amount of received income per category and percentage from total.
     * @return HashMap
     * @throws java.sql.SQLException
     */
    @Override
    public HashMap<String, ArrayList<Double>> incomeForEachCategory(Integer userId) throws SQLException {
        HashMap<String, ArrayList<Double>> overview = createOverview();
        Connection connection = this.connect();
        String sql = "SELECT DISTINCT category, SUM(amount) AS sum, SUM(Amount) * 100.0 /"
                + " (SELECT SUM(Amount) FROM Income JOIN Account ON Account.id = Income.account_id WHERE Account.id = ?) AS percentage"
                + " FROM Income JOIN Account ON Account.id = Income.account_id WHERE Account.id = ? GROUP BY category";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, userId);
        stmt.setInt(2, userId);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            String category = rs.getString("category");
            overview.get(category).add(rs.getDouble("sum"));
            overview.get(category).add(rs.getDouble("percentage"));
        }
        closeConnection(stmt, rs, connection);
        return overview;
    }

    /**
     * Controls, if there is an existing income to the given data.
     * @param date date, when income was paid
     * @param amount decimal number between 0.0 and 9999999.99
     * @param category name of the category from the given list
     * @param userId id from the current user
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
     * Search ten by date newest incomes from database for the current user.
     * @param userId id from the current user
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

    // closes connecttion to the database
    private void closeConnection(PreparedStatement stmt, ResultSet rs, Connection connection) throws SQLException {
        stmt.close();
        rs.close();
        connection.close();
    }
    
    private void createIncomeTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Income("
                + " id integer PRIMARY KEY AUTOINCREMENT,"
                + " account_id INTEGER,"
                + " date DATE," 
                + " category VARCHAR(100) NOT NULL,"
                + " amount NUMERIC(10,2),"
                + " FOREIGN KEY (account_id) REFERENCES Account(id)"
                + ");";
        Connection connection = DriverManager.getConnection(database); 
        Statement stmt = connection.createStatement();
        stmt.execute(sql);
        stmt.close();
        connection.close();
    
    }

    private HashMap<String, ArrayList<Double>> createOverview() {
        HashMap<String, ArrayList<Double>> overview = new HashMap<>();
        overview.putIfAbsent("Other", new ArrayList<>());
        overview.put("Salary", new ArrayList<>());
        overview.put("Present", new ArrayList<>());
        
        return overview;
    }
    
}
