package financialmanagement.domain;

import financialmanagement.dao.ExpenseDao;
import financialmanagement.dao.IncomeDao;
import financialmanagement.dao.UserDao;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * This class  
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
    public boolean createUser(String username) {
        if (userDao.findByUsername(username) != null) {
            return false;
        }
        User user = new User(username);
        try {
            userDao.create(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
    
    //login function
    public boolean login(String username) {
        User user = userDao.findByUsername(username);
        if (user == null) {
            return false;
        }
        loggedIn = user;
        return true;
    }
    
    // returns user, which is logged in.
    public User getLoggedUser() {
        return loggedIn;
    }
    
    // Logout function
    public void logout() {
        loggedIn = null;
    }
    // End of User methods
    
    // All Income related methods below
    
    // returns if creating of the income was succesfull.
    public boolean createIncome(LocalDateTime datetime, Double amount, String category, Integer userid) {
        if (incomeDao.findIncome(datetime, amount, category, userid) != null) {
            return false;
        }
        Income income = new Income(datetime, amount, category, userid);
        try {
            incomeDao.create(income);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }

    public List<Income> listIncomes(Integer userId) {
        List<Income> incomes = incomeDao.getTenResentAdded(userId);
        return incomes;
    }
    
    // All Expense related methods below
    public boolean createExpense(LocalDateTime datetime, Double amount, String category, Integer userid) {
        if (expenseDao.findExpense(datetime, amount, category, userid) != null) {
            return false;
        }
        Expense expense = new Expense(datetime, amount, category, userid);
        try {
            expenseDao.create(expense);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }
  
    public List<Expense> listExpenses(Integer userId) {
        List<Expense> expenses = expenseDao.getTenResentlyAdded(userId);
        return expenses;
    }
}
