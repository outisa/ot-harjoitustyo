
package financialmanagement.dao;

import financialmanagement.domain.Income;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Interface for SQLIncomeDao.
 */
public interface IncomeDao {
    /**
     * Creates a new income.
     * @param income new income, which needs to be created
     * @throws Exception if there is database related errors
     */
    void create(Income income) throws Exception;
    
    /**
     * Collects incomes for each category.
     * @param userId id from the current user
     * @return HashMap, in which key is category and value is ArrayList, which includes
     * incomes as amount and percentage of total per category
     * @throws Exception if there is database related errors
     */
    HashMap<String, ArrayList<Double>> incomeForEachCategory(Integer userId) throws Exception;
    
    /**
     * Searches if given income does already exists.
     * @param date date, when income was paid
     * @param amount decimal number between 0 and 9999999.99
     * @param category name of the category
     * @param userId id of the current user
     * @return  user if already exists, otherwise null
     * @throws java.lang.Exception if there is database related errors
     */
    Income findIncome(Date date, Double amount, String category, Integer userId) throws Exception;
    
    /**
     * Lists ten by date newest incomes.
     * @param userId id of the current user
     * @return list of the incomes
     * @throws java.lang.Exception if there is database related errors
     */
    List<Income> getTenResentAdded(Integer userId) throws Exception;
}
