
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
        List<Expense> allExpenses = generateExpensesList();
        List<Expense> expensesBetween = new ArrayList<>();
        for (int i = 0; i < allExpenses.size(); i++) {
            if (allExpenses.get(i).getDate().before(dateTo) && allExpenses.get(i).getDate().after(dateFrom) && allExpenses.get(i).getUserId() == 1) {
                expensesBetween.add(allExpenses.get(i));
            }
        }
        return expensesBetween;
    }

    @Override
    public List<Expense> getTenResentlyAdded(Integer userId) {
        List<Expense> expensesForCurrentUser = new ArrayList<>();
        expenses.stream().filter((expense) -> (expense.getUserId().equals(userId))).forEachOrdered((expense) -> {
            expensesForCurrentUser.add(expense);
        });
        return expensesForCurrentUser;
    }

    @Override
    public HashMap<String, ArrayList<Double>> expenseForEachCategory(Integer userId) throws Exception {
        HashMap<String, ArrayList<Double>> expensesPerCategory = new HashMap<>();
        List<Expense> allExpenses = generateExpensesList();
        double food = 0;
        double education = 0; 
        for (int i = 0; i < allExpenses.size(); i++) {
            if (allExpenses.get(i).getCategory().equals("Food") && allExpenses.get(i).getUserId().equals(userId)) {
                food += allExpenses.get(i).getAmount();
            } else if (allExpenses.get(i).getCategory().equals("Education") && allExpenses.get(i).getUserId().equals(userId)) {
                education += allExpenses.get(i).getAmount();
            }            
        }
        
        expensesPerCategory.putIfAbsent("Food", new ArrayList<>());
        expensesPerCategory.putIfAbsent("Education", new ArrayList<>());
        expensesPerCategory.get("Food").add(food);
        expensesPerCategory.get("Food").add(food/(food+education));
        expensesPerCategory.get("Education").add(education);
        expensesPerCategory.get("Education").add(education/(food+education));        
        return expensesPerCategory;
    }
    
    private List<Expense> generateExpensesList() {
        List<Expense> generated = new ArrayList<>();
        Date date = Date.valueOf("2019-01-04");
        for (int i = 0; i < 3; i++) {
            generated.add(new Expense(1, date, "Food", 100.00));
        }
        date = Date.valueOf("2019-07-04");
        for (int i = 0; i < 5; i++) {
            generated.add(new Expense(1, date, "Education", 100.00));
        }
        generated.add(new Expense(3, date, "Food", 100.00));        
        return generated;
    }
    
}
