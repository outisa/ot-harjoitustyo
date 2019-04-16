
package financialmanagement.domain;

import financialmanagement.dao.IncomeDao;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class FakeIncomeDao implements IncomeDao {
    private List<Income> incomes;

    public FakeIncomeDao() {
        incomes = new ArrayList<>();
        Date date = Date.valueOf("2019-12-24");
        Income income = new Income(1, date, "Present", 120.00);
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
    public Income findIncome(Date date, Double amount, String category, Integer userId) {
        Income newIncome = new Income(userId, date, category, amount);
        for (Income income: incomes) {
            if (newIncome.equals(income)) {
                return income;
            }    
        }
        return null;
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
