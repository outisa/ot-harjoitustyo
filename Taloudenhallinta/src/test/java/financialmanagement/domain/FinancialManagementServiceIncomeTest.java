/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financialmanagement.domain;

import java.sql.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author ousavola
 */
public class FinancialManagementServiceIncomeTest {
    FakeUserDao userDao;
    FakeIncomeDao incomeDao;
    FakeExpenseDao expenseDao;
    FinancialManagementService service;

    @Before
    public void setUp() {
        userDao = new FakeUserDao();
        incomeDao = new FakeIncomeDao();
        expenseDao = new FakeExpenseDao();
        service = new FinancialManagementService(userDao, incomeDao, expenseDao);
    }
      
    @Test
    public void incomeCanBeCreated() throws Exception {
        Date date = Date.valueOf("2019-12-01");
        assertTrue(service.createIncome(date, 12.90, "Other", 1));
        
    }
    @Test 
    public void incomeCanNotBeCreatedIfAlreadyExists() throws Exception {
        Date date = Date.valueOf("2019-12-24");
        assertFalse(service.createIncome(date, 120.00, "Present", 1));    
    }
    
    @Test
    public void incomeListIsReturned() throws Exception {
        assertEquals(1, service.listIncomes(1).size());
    }
    
    @Test
    public void incomePerCategoryForUserIncludesRightData() throws Exception {
        assertEquals(120, service.overviewIncomes(1).get("Present").get(0), 1);          
    }
      
}
