package financialmanagement.domain;

import financialmanagement.dao.ExpenseDao;
import financialmanagement.dao.IncomeDao;
import financialmanagement.dao.UserDao;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

/**
 * This class manages application logic.
 */

public class FinancialManagementService {
    private User loggedIn;
    private UserDao userDao;
    private IncomeDao incomeDao;
    private ExpenseDao expenseDao;
    
    /**
     * Constructor creates new FinancialManagemntService object.
     * @param userDao interface between user related data management and application logic
     * @param incomeDao interface between income related data management and application logic
     * @param expenseDao interface between expense related data management and application logic
     */
    public FinancialManagementService(UserDao userDao, IncomeDao incomeDao, ExpenseDao expenseDao) {
        this.userDao = userDao;
        this.incomeDao = incomeDao;
        this.expenseDao = expenseDao;
    }
    
    // All the User related methods below.
    /**
     * Creates new user, if the username is unique.
     * @param username username for the new user
     * @return false, if there is already user with the same name, otherwise true
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
     * @param username username from the user, who wants to log in
     * @return true, if user exists and was successfully locked in, otherwise false
     * @throws Exception 
     */
    public boolean login(String username) throws Exception {
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

    // All Income related methods below
    
    /**
     * Creates a new income for a current user.
     * @param datetime date, when income was paid
     * @param amount decimal number between 0.0 and 9999999.99
     * @param category name of the category
     * @param userid id from the current user
     * @return true, if the income was successfully added and false, if income already exists.
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
     * @param userId id from the current user
     * @return list of incomes
     */
    public List<Income> listIncomes(Integer userId) {
        List<Income> incomes = incomeDao.getTenResentAdded(userId);
        return incomes;
    }
    
    // All Expense related methods below
    /**
     * Creates a new expense, if not exists.
     * @param datetime date when expense was paid
     * @param amount decimal number between 0 - 9999999.99
     * @param category name of the category
     * @param userid id for the current user
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
     * Lists all the expenses for the current user between the given dates.
     * @param accountId -id from the current user
     * @param dateFrom -begin date of the search
     * @param dateTo -end date of the search
     * @return list of the expenses between given dates.
     */
    public List<Expense> listExpensesBetween(Integer accountId, Date dateFrom, Date dateTo) {
        List<Expense> expensesBetween = expenseDao.getAllBetween(dateFrom, dateTo, accountId);
        return expensesBetween;
    }
    /**
     * Lists by date ten newest expenses for the current user.
     * @param userId id for the current user
     * @return list of expenses
     */
    public List<Expense> listExpenses(Integer userId) {
        List<Expense> expenses = expenseDao.getTenResentlyAdded(userId);
        return expenses;
    }
}
