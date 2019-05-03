
package financialmanagement.dao;

import financialmanagement.domain.Expense;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public interface ExpenseDao {
    
    /**
     * Creates new expense.
     * @param expense new expense, which needs to be created
     * @throws Exception if there is database related errors
     */
    void create(Expense expense) throws Exception;
    
    /**
     * Lists all expenses for each category.
     * @param userId id from the current user
     * @return HashMap, which includes name of the category and value is
     * ArrayList, which contains amount and percentage of total
     * @throws Exception if there is database related errors
     */
    HashMap<String, ArrayList<Double>> expenseForEachCategory(Integer userId) throws Exception;
    
    /**
     * Searches if given expense exists.
     * @param date date when expense was paid
     * @param amount decimal number between 0 and 9999999.99
     * @param category name of the category
     * @param userId id from the current user
     * @return expense if found, otherwise null
     * @throws java.lang.Exception if there is database related errors
     */
    Expense findExpense(Date date, Double amount, String category, Integer userId) throws Exception;
    
    /**
     * Searches expenses between given dates for the current user.
     * @param dateFrom begin date of the search
     * @param dateTo end date of the search
     * @param userId id from the current user
     * @return result of the search as list
     * @throws java.lang.Exception if there is database related errors
     */
    List<Expense> getAllBetween(Date dateFrom, Date dateTo, Integer userId) throws Exception;
    
    /**
     * Lists newest ten expenses.
     * @param userId id from the current user
     * @return list of the expenses
     * @throws java.lang.Exception if there is database related errors
     */
    List<Expense> getTenResentlyAdded(Integer userId) throws Exception;
}
