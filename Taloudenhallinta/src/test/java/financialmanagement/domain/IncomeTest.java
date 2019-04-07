
package financialmanagement.domain;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IncomeTest {
    Income income;
    Income income2;
    Income income3;
    
    @Before
    public void setUp() {
        LocalDateTime date = LocalDateTime.parse("2019-12-12"+ " " + "00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.income = new Income(date, 128.22, "Present", 2);
        this.income2= new Income(date, 128.22, "Present", 2);
        this.income3= new Income(date, 12.12, "Salary", 1);
    }
    
    @Test
    public void incomeHasDate() {
        assertEquals(LocalDateTime.parse("2019-12-12" + " " + "00:00" ,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), income.getDatetime());
    }
    
    @Test
    public void incomeHasAmout(){
        assertEquals(128.22, income.getAmount(), 0.001);
    }
    
    @Test
    public void incomeHasCategory(){
        assertEquals("Present", income.getCategory());
    }
    
    @Test
    public void incomeHasUserId() {
        assertTrue(income.getUserId().equals(2));
    }
    
    @Test
    public void incomeEqualsWhenSameParameters(){
        assertTrue(income.equals(income2));
    }
    
    @Test
    public void incomeNonequalsWhenAllParametersNotSame(){
        assertFalse(income.equals(income3));
    }
    
    @Test
    public void incomeNonequalWithDifferentType() {
        Object o = new Object();
        assertFalse(income.equals(o));
    }
         
}
