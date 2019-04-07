
package financialmanagement.dao;

import financialmanagement.domain.Expense;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;


public interface ExpenseDao {
    void create(Expense expense) throws Exception;
    
    HashMap<String, Integer> expenseForEachCategory();
    
    Expense findExpense(LocalDateTime date, Double amount, String category, Integer userId);
    
    List<Expense> getAllBetween(LocalDateTime dateFrom, LocalDateTime dateTo, Integer userId);
    
    List<Expense> getTenResentlyAdded(Integer userId);
}
