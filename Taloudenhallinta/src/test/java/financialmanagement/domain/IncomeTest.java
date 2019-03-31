
package financialmanagement.domain;


import java.sql.Date;
import java.text.DateFormat;
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
        assertEquals(income.getDatetime(), LocalDateTime.parse("2019-12-12" + " " + "00:00" ,DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }
    
    @Test
    public void incomeHasAmout(){
        assertEquals(income.getAmount(), 128.22, 0.001);
    }
    
    @Test
    public void incomeHasCategory(){
        assertEquals(income.getCategory(), "Present");
    }
    
    @Test
    public void incomeHasUserId() {
        assertEquals(income.getUserId(), 2, 0);
    }
    
    @Test
    public void incomeEqualsWhenSameParameters(){
        assertEquals(income.equals(income2), true);
    }
    
    @Test
    public void incomeNonequalsWhenAllParametersNotSame(){
        assertEquals(income.equals(income3), false);
    }
    
    @Test
    public void incomeNonequalWithDifferentType() {
        Object o = new Object();
        assertFalse(income.equals(o));
    }
         
}
