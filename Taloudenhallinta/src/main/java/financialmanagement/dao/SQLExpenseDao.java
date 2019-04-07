
package financialmanagement.dao;

import financialmanagement.domain.Expense;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import jdk.nashorn.internal.ir.LiteralNode;


public class SQLExpenseDao implements ExpenseDao {
    private List<Expense> expenses;

    public SQLExpenseDao() {
        expenses = new ArrayList<>();
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
        for (Expense expense: expenses) {
            if (expense.getUserId().equals(userId)) {
                expensesForCurrentUser.add(expense);
            }
        }
        return expensesForCurrentUser;
    }
    
}
