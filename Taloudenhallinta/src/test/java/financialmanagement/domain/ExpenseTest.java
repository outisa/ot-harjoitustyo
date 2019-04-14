
package financialmanagement.domain;

import java.sql.Date;
import java.time.LocalDateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class ExpenseTest {
    Expense expense;
    Expense expense2;
    Expense expense3;
   
    @Before
    public void setUp() {
        Date date = Date.valueOf("2019-12-12");
        this.expense = new Expense(2, date, "Food", 10.78);
        this.expense2= new Expense(2, date, "Food", 10.78);
        this.expense3= new Expense(1, date, "Household", 109.00);
    }
    
    @Test
    public void expenseHasDate() {
        assertEquals(Date.valueOf("2019-12-12"), expense.getDate());
    }
    
    @Test
    public void expenseHasAmout(){
        Assert.assertEquals(10.78, expense.getAmount(), 0.00001);
    }
    
    @Test
    public void expenseHasCategory(){
        assertEquals("Food", expense.getCategory());
    }
    
    @Test
    public void expenseHasUserId() {
        assertEquals(2, expense.getUserId(), 0.0);
    }
    
    @Test
    public void expenseEqualsWhenSameParameters(){
        assertTrue(expense.equals(expense2));
    }
    
    @Test
    public void expenseNonequalsWhenAllParametersNotSame(){
        assertFalse(expense.equals(expense3));
    }
    
    @Test
    public void expenseNonequalWhenDifferentType(){
        Object obj = new Object();
        assertFalse(expense.equals(obj));
    }
        
}
