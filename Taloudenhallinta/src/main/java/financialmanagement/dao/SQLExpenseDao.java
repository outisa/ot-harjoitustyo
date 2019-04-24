
package financialmanagement.dao;

import financialmanagement.domain.Expense;
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
 * Communicates with database with expense related data.
 */
public class SQLExpenseDao implements ExpenseDao {
    private String database;
     
    /**
     * Constructor creates Expense table, if not already exists.
     * 
     * @param database database, which will be used in the methods of this class
     * @throws Exception if something goes wrong by creating connection to the database
     */
    public SQLExpenseDao(String database) throws Exception {
        this.database = database;
        createExpenseTable();
    }
    
    /**
     * Connect to the database.
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
     * Inserts a new expense in to the database.
     * 
     * @param expense expense, which will be inserted into database
     * 
     * @throws Exception if something goes wrong with the database
     */
    @Override
    public void create(Expense expense) throws Exception {
        String sql = "INSERT INTO Expense(account_id, date, category, amount) VALUES (?, ?, ?, ?)";
        try (Connection connection = this.connect()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, expense.getUserId());
            stmt.setDate(2, expense.getDate());
            stmt.setString(3, expense.getCategory());
            stmt.setDouble(4, expense.getAmount());
            stmt.executeUpdate();
            stmt.close();
            connection.close();
        }
    }

   
    /**
     * Controls, if there is an expense for given parameters.
     * 
     * @param date date, when expense was paid
     * @param amount in form xxx.xx, a positive decimal number
     * @param category name of the given category
     * @param userId automatic generated user id from the current user.
     * 
     * @return null if nothing was found, expense if expense was found.
     */
    @Override
    public Expense findExpense(Date date, Double amount, String category, Integer userId) {
        List<Expense> expenses = new ArrayList<>();
        Expense newExpense = new Expense(userId, date, category, amount);
        String sql = "SELECT * FROM Expense";
        try (Connection connection = this.connect(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                expenses.add(new Expense(rs.getInt("account_id"), rs.getDate("date"), rs.getString("category"), rs.getDouble("amount")));
            }
            closeConnection(stmt, rs, connection);
        } catch (SQLException ex) {
            Logger.getLogger(SQLExpenseDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Expense expense: expenses) {
            if (expense.equals(newExpense)) {
                return expense;                
            }
        }
        return null;
    }

    /**
     * Gives all expenses between given dates.
     * 
     * @param dateFrom date, begin of the search
     * @param dateTo date, end of the search
     * @param userId id from the current user
     * 
     * @return list of the expenses from the current user
     */
    @Override
    public List<Expense> getAllBetween(Date dateFrom, Date dateTo, Integer userId) {
        List<Expense> expenses = new ArrayList<>();
        String sql = "SELECT * FROM Expense WHERE account_id = ? AND date >= ? AND date < ? ORDER BY date";
        try (Connection connection = this.connect()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setDate(2, dateFrom);
            stmt.setDate(3, dateTo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                expenses.add(new Expense(rs.getInt("account_id"), rs.getDate("date"), rs.getString("category"), rs.getDouble("amount")));
            }            
            closeConnection(stmt, rs, connection);
        } catch (SQLException ex) {
            Logger.getLogger(SQLExpenseDao.class.getName()).log(Level.SEVERE, null, ex);           
        }
        return expenses;
    }
    
     /**
     * Creates HashMap, which keys are categories and values are ArrayLists, 
     * which contains amount of received expenses per category and percentage of total.
     * @return categories with category related data
     * @throws java.sql.SQLException if there is any database related problems
     */
    @Override
    public HashMap<String, ArrayList<Double>> expenseForEachCategory(Integer userId) throws SQLException {
        HashMap<String, ArrayList<Double>> overview = createOverview();
        Connection connection = this.connect();
        String sql = "SELECT DISTINCT category, SUM(amount) AS sum, SUM(Amount) * 100.0 /"
                + " (SELECT SUM(Amount) FROM Expense JOIN Account ON Account.id = Expense.account_id WHERE Account.id = ?) AS percentage"
                + " FROM Expense JOIN Account ON Account.id = Expense.account_id WHERE Account.id = ? GROUP BY category";
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
     * Search ten by date newest expenses from database for current user.
     * 
     * @param userId id from the current user
     * 
     * @return list of expenses 
     */
    @Override
    public List<Expense> getTenResentlyAdded(Integer userId) {
        List<Expense> expensesForCurrentUser = new ArrayList<>();
        String sql = "SELECT * FROM Expense WHERE account_id = ? ORDER BY date DESC LIMIT 10";
        try (Connection connection = this.connect()) {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                expensesForCurrentUser.add(new Expense(rs.getInt("account_id"), rs.getDate("date"), rs.getString("category"), rs.getDouble("amount")));
            }            
            closeConnection(stmt, rs, connection);
        } catch (SQLException ex) {
            Logger.getLogger(SQLExpenseDao.class.getName()).log(Level.SEVERE, null, ex);           
        }
        return expensesForCurrentUser;
    }
    
    /**
     * Creates Expense table into the database, if not already exists.
     */
    private void createExpenseTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Expense("
                + " id integer PRIMARY KEY AUTOINCREMENT,"
                + " account_id INTEGER NOT NULL,"
                + " date DATE NOT NULL," 
                + " category VARCHAR(30) NOT NULL,"
                + " amount NUMERIC(9,2) NOT NULL,"
                + " FOREIGN KEY (account_id) REFERENCES Account(id)"
                + ");";
        try (Connection connection = DriverManager.getConnection(database); Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            stmt.close();
            connection.close();
        }
    }

    private void closeConnection(PreparedStatement stmt, ResultSet rs, Connection connection) throws SQLException {
        stmt.close();
        rs.close();
        connection.close();
    }

    private HashMap<String, ArrayList<Double>> createOverview() {
        HashMap<String, ArrayList<Double>> overview = new HashMap<>();
        overview.putIfAbsent("Other", new ArrayList<>());
        overview.putIfAbsent("Food", new ArrayList<>());
        overview.putIfAbsent("Home", new ArrayList<>());
        overview.putIfAbsent("Car", new ArrayList<>());
        overview.putIfAbsent("Hobbies", new ArrayList<>());
        overview.putIfAbsent("Education", new ArrayList<>());
        overview.putIfAbsent("Restaurants", new ArrayList<>());
        overview.putIfAbsent("Travelling", new ArrayList<> ());
        overview.putIfAbsent("Insurances", new ArrayList<>());
        overview.putIfAbsent("Other", new ArrayList<>());        
                                
        return overview;
    }

    
}
