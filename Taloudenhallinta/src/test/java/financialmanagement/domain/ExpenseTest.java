
package financialmanagement.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        this.expense = new Expense(date, 10.78, "Food", 2 , " ");
        this.expense2= new Expense(date, 10.78, "Food", 2, " ");
        this.expense3= new Expense(date, 109.00, "Household", 1, " ");
    }
    
    @Test
    public void expenseHasDate() {
        assertEquals(expense.getDate(), LocalDateTime.parse("2019-12-12" + " " + "00:00" ,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }
    
    @Test
    public void expenseHasAmout(){
        assertEquals(expense.getAmount(), 10.78, 0.001);
    }
    
    @Test
    public void expenseHasCategory(){
        assertEquals(expense.getCategory(), "Food");
    }
    
    @Test
    public void expenseHasUserId() {
        assertEquals(expense.getUserId(), 2, 0);
    }
    
    @Test
    public void expenseEqualsWhenSameParameters(){
        assertEquals(expense.equals(expense2), true);
    }
    
    @Test
    public void expenseNonequalsWhenAllParametersNotSame(){
        assertEquals(expense.equals(expense3), false);
    }
    
    @Test
    public void expenseNonequalWhenDifferentType(){
        Object obj = new Object();
        assertFalse(expense.equals(obj));
    }
        
}
