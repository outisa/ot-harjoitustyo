
package financialmanagement.domain;

import financialmanagement.dao.ExpenseDao;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class FakeExpenseDao implements ExpenseDao{
    List<Expense> expenses;

    public FakeExpenseDao() {
        expenses = new ArrayList<>();
        LocalDateTime date = LocalDateTime.parse("2019-07-26"+ " " + "00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        expenses.add(new Expense(date, 12.05, "Food", 1));
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
    public Expense findExpense(LocalDateTime date, Double amount, String category, Integer userId) {
        Expense newExpense = new Expense(date, amount, category, userId);
        for (Expense expense: expenses) {
            if (newExpense.equals(expense)) {
                return expense;
            }    
        }
        return null; 
    }

    @Override
    public List<Expense> getAllBetween(LocalDateTime dateFrom, LocalDateTime dateTo, Integer userId) {
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
