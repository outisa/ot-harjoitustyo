
package financialmanagement.domain;

import financialmanagement.dao.ExpenseDao;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class FakeExpenseDao implements ExpenseDao{
    List<Expense> expenses;

    public FakeExpenseDao() {
        expenses = new ArrayList<>();
        Date date = Date.valueOf("2019-07-26");
        expenses.add(new Expense(1, date, "Food", 12.05));
    }

    @Override
    public void create(Expense expense) throws Exception {
        expenses.add(expense);
    }

    @Override
    public HashMap<String, Integer> expenseForEachCategory() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Expense findExpense(Date date, Double amount, String category, Integer userId) {
        Expense newExpense = new Expense(userId, date, category, amount);
        for (Expense expense: expenses) {
            if (newExpense.equals(expense)) {
                return expense;
            }    
        }
        return null; 
    }

    @Override
    public List<Expense> getAllBetween(Date dateFrom, Date dateTo, Integer userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Expense> getTenResentlyAdded(Integer userId) {
        List<Expense> expensesForCurrentUser = new ArrayList<>();
        expenses.stream().filter((expense) -> (expense.getUserId().equals(userId))).forEachOrdered((expense) -> {
            expensesForCurrentUser.add(expense);
        });
        return expensesForCurrentUser;
    }
    
}
