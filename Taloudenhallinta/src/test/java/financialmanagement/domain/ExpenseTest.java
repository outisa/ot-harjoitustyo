
package financialmanagement.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        LocalDateTime date = LocalDateTime.parse("2019-12-12"+ " " + "00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.expense = new Expense(date, 10.78, "Food", 2);
        this.expense2= new Expense(date, 10.78, "Food", 2);
        this.expense3= new Expense(date, 109.00, "Household", 1);
    }
    
    @Test
    public void expenseHasDate() {
        assertEquals(LocalDateTime.parse("2019-12-12" + " " + "00:00" ,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), expense.getDate());
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
