
package financialmanagement.dao;

import financialmanagement.domain.Income;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

public interface IncomeDao {
    void create(Income income) throws Exception;
    
    HashMap<String, Integer> incomeForEachCategory();
    
    Income findIncome(LocalDateTime date, Double amount, String category, Integer userId);
    
    List<Income> getAllBetween(LocalDateTime dateFrom, LocalDateTime dateTo, Integer userId);
    
    List<Income> getTenResentAdded(Integer userId);
}
