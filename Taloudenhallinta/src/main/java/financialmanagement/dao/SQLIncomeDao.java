package financialmanagement.dao;

import financialmanagement.domain.Income;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SQLIncomeDao implements IncomeDao {
    private List<Income> incomes;

    public SQLIncomeDao() {
        incomes = new ArrayList<>();
    }

    @Override
    public void create(Income income) throws Exception {
        incomes.add(income);
    }

    @Override
    public HashMap<String, Integer> incomeForEachCategory() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Income findIncome(LocalDateTime date, Double amount, String category, Integer userId) {
        Income newIncome = new Income(date, amount, category, userId);
        for (Income income: incomes) {
            if (newIncome.equals(income)) {
                return income;
            }    
        }
        return null;
    }

    @Override
    public List<Income> getAllBetween(LocalDateTime dateFrom, LocalDateTime dateTo, Integer userId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Income> getTenResentAdded(Integer userId)  {
        List<Income> incomessForCurrentUser = new ArrayList<>();
        for (Income income: incomes) {
            if (income.getUserId().equals(userId)) {
                incomessForCurrentUser.add(income);
            }
        }
        return incomessForCurrentUser;
    }
    
}
