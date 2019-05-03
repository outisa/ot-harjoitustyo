
package financialmanagement.domain;

import financialmanagement.dao.IncomeDao;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


class FakeIncomeDao implements IncomeDao {
    private List<Income> incomes;

    public FakeIncomeDao() {
        this.incomes = new ArrayList<>();
        Date date = Date.valueOf("2019-12-24");
        Income income = new Income(1, date, "Present", 120.00);
        this.incomes.add(income); 
    }
    
    @Override
    public void create(Income income) throws Exception {
        incomes.add(income);
    }

    @Override
    public HashMap<String, ArrayList<Double>> incomeForEachCategory(Integer userId) {
        HashMap<String, ArrayList<Double>> overview = new HashMap<>();
        incomes.add(new Income(1, Date.valueOf("2019-09-01"), "Salary", 2356.12));
        incomes.add(new Income(2, Date.valueOf("2019-08-11"), "Present", 100.00));
        double salary = 0;
        double present = 0;
        for (int i = 0; i < incomes.size(); i++) {
            if (incomes.get(i).getCategory().equals("Present") && incomes.get(i).getUserId().equals(userId)) {
                present += incomes.get(i).getAmount();                
            } else if (incomes.get(i).getCategory().equals("Salary") && incomes.get(i).getUserId().equals(userId)) {
                salary += incomes.get(i).getAmount();
            }
        }

        overview.putIfAbsent("Salary", new ArrayList<>());
        overview.putIfAbsent("Present", new ArrayList<>());
        overview.get("Salary").add(salary);
        overview.get("Salary").add(salary * 100 / (salary + present));        
        overview.get("Present").add(present);
        overview.get("Present").add(present * 100 / (salary + present));        
        return overview; 
    }

    @Override
    public Income findIncome(Date date, Double amount, String category, Integer userId) throws Exception {
        Income newIncome = new Income(userId, date, category, amount);
        for (Income income: incomes) {
            if (newIncome.equals(income)) {
                return income;
            }    
        }
        return null;
    }

    @Override
    public List<Income> getTenResentAdded(Integer userId) throws Exception {
        List<Income> incomessForCurrentUser = new ArrayList<>();
        incomes.stream().filter((income) -> (income.getUserId().equals(userId))).forEachOrdered((income) -> {
            incomessForCurrentUser.add(income);
        });
        return incomessForCurrentUser;
    }
    
}
