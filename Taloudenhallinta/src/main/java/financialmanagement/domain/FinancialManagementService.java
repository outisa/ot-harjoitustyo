package financialmanagement.domain;

import financialmanagement.dao.ExpenseDao;
import financialmanagement.dao.IncomeDao;
import financialmanagement.dao.UserDao;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class manages application logic
 */

public class FinancialManagementService {
    private User loggedIn;
    private UserDao userDao;
    private IncomeDao incomeDao;
    private ExpenseDao expenseDao;

    public FinancialManagementService(UserDao userDao, IncomeDao incomeDao, ExpenseDao expenseDao) {
        this.userDao = userDao;
        this.incomeDao = incomeDao;
        this.expenseDao = expenseDao;
    }
    
    // All the User related functions below.
    /**
     * Creates new user, if the username is unique.
     * 
     * @param username
     * @return false, if there is already user with the same name; 
     * true, if user was successfully created.
     * @throws Exception 
     */
    public boolean createUser(String username) throws Exception {
        if (userDao.findByUsername(username) != null) {
            return false;
        }
        User user = new User(username);
        try {
            userDao.create(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    
    /**
     * Logs in the user with given username.
     * 
     * @param username
     * @return true, if user exists and was successfully locked in; false, if user does not exist
     */
    public boolean login(String username) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            return false;
        }
        loggedIn = user;
        return true;
    }
    
    /**
     * Gets currently logged user.
     * @return loggedIn  User, which is currently logged in  
     */
    public User getLoggedUser() {
        return loggedIn;
    }
    
    /**
     * Logs out the current user.
     */
    public void logout() {
        loggedIn = null;
    }
    // End of User methods
    
    // All Income related methods below
    
    /**
     * Creates a new income for a current user.
     * @param datetime
     * @param amount
     * @param category
     * @param userid
     * @return true, if the income was successfully added; false, if income already exists.
     * @throws Exception 
     */
    public boolean createIncome(Date datetime, Double amount, String category, Integer userid) throws Exception {
        if (incomeDao.findIncome(datetime, amount, category, userid) != null) {
            return false;
        }
        Income income = new Income(userid, datetime, category, amount);
        try {
            incomeDao.create(income);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
    /**
     * Lists ten by date newest incomes. 
     * @param userId
     * @return list of incomes
     */
    public List<Income> listIncomes(Integer userId) {
        List<Income> incomes = incomeDao.getTenResentAdded(userId);
        return incomes;
    }
    
    // All Expense related methods below
    /**
     * Creates new expense, if not exists.
     * @param datetime
     * @param amount
     * @param category
     * @param userid
     * @return false, if given expense already exists; true, if expense was successfully created
     * @throws Exception 
     */
    public boolean createExpense(Date datetime, Double amount, String category, Integer userid) throws Exception {
        if (expenseDao.findExpense(datetime, amount, category, userid) != null) {
            return false;
        }
        Expense expense = new Expense(userid, datetime, category, amount);
        try {
            expenseDao.create(expense);
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
    
    /**
     *
     * @param accountId
     * @param dateFrom
     * @param dateTo
     * @return
     */
    public List<Expense> listExpensesBetween(Integer accountId, Date dateFrom, Date dateTo) {
        List<Expense> expensesBetween = expenseDao.getAllBetween(dateFrom, dateTo, accountId);
        return expensesBetween;
    }
    /**
     * Lists by date ten newest expenses.
     * @param userId
     * @return list of expenses
     */
    public List<Expense> listExpenses(Integer userId) {
        List<Expense> expenses = expenseDao.getTenResentlyAdded(userId);
        return expenses;
    }
}
