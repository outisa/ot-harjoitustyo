
package financialmanagement.dao;

import financialmanagement.domain.Income;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IncomeDao {
    /**
     * Creates a new income.
     * @param income new income, which needs to be created
     * @throws Exception 
     */
    void create(Income income) throws Exception;
    
    /**
     * Lists incomes for each category.
     * @param userId id from the current user
     * @return HashMap, in which key is category and value is ArrayList, which includes
     * incomes as amount and percentage from total per category
     * @throws Exception 
     */
    HashMap<String, ArrayList<Double>> incomeForEachCategory(Integer userId) throws Exception;
    
    /**
     * Searches if given income does already exists.
     * @param date date, when income was paid
     * @param amount decimal number between 0 and 9999999.99
     * @param category name of the category
     * @param userId id of the current user
     * @return  user if already exists, otherwise null
     */
    Income findIncome(Date date, Double amount, String category, Integer userId);
    
    /**
     * Lists ten by date newest incomes.
     * @param userId id of the current user
     * @return list of the incomes
     */
    List<Income> getTenResentAdded(Integer userId);
}
