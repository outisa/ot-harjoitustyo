
package financialmanagement.dao;

import financialmanagement.domain.Income;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;

public interface IncomeDao {
    void create(Income income) throws Exception;
    
    HashMap<String, Integer> incomeForEachCategory() throws Exception;
    
    Income findIncome(Date date, Double amount, String category, Integer userId);
    
    List<Income> getTenResentAdded(Integer userId);
}
