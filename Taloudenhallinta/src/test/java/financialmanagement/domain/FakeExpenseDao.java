
package financialmanagement.domain;

import financialmanagement.dao.ExpenseDao;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;


class FakeExpenseDao implements ExpenseDao{

    @Override
    public void create(Expense expense) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HashMap<String, Integer> expenseForEachCategory() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expense findExpense(LocalDateTime date, Double amount, String category, Integer userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Expense> getAllBetween(LocalDateTime dateFrom, LocalDateTime dateTo, Integer userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Expense> getTenResentlyAdded(Integer userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
