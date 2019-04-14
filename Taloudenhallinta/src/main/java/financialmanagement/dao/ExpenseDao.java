
package financialmanagement.dao;

import financialmanagement.domain.Expense;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;


public interface ExpenseDao {
    void create(Expense expense) throws Exception;
    
    HashMap<String, Integer> expenseForEachCategory() throws Exception;
    
    Expense findExpense(Date date, Double amount, String category, Integer userId) throws Exception;
    
    List<Expense> getAllBetween(Date dateFrom, Date dateTo, Integer userId);
    
    List<Expense> getTenResentlyAdded(Integer userId);
}
