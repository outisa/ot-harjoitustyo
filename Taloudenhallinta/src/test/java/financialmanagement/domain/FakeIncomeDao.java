
package financialmanagement.domain;

import financialmanagement.dao.IncomeDao;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class FakeIncomeDao implements IncomeDao {
    private List<Income> incomes;

    public FakeIncomeDao() {
        incomes = new ArrayList<>();
        LocalDateTime date = LocalDateTime.parse("2019-12-24"+ " " + "00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        Income income = new Income(date, 120.00, "Present", 1);
        incomes.add(income);
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
    public List<Income> getTenResentAdded(Integer userId) {
        List<Income> incomessForCurrentUser = new ArrayList<>();
        incomes.stream().filter((income) -> (income.getUserId().equals(userId))).forEachOrdered((income) -> {
            incomessForCurrentUser.add(income);
        });
        return incomessForCurrentUser;
    }
    
}
