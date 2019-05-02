
package financialmanagement.domain;


import java.sql.Date;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IncomeTest {
    Income income;
    Income income2;
    Income income3;
    Income income4;
    
    @Before
    public void setUp() {
        Date date = Date.valueOf("2019-12-12");
        Date date2 = Date.valueOf("2018-12-01");
        this.income = new Income(2, date, "Present", 128.22);
        this.income2= new Income(2, date, "Present", 128.22);
        this.income3= new Income(1, date2, "Salary", 12.12);
        this.income4 = new Income(2, date2, "Present", 128.22);
    }
    
    @Test
    public void incomeHasDate() {
        assertEquals(Date.valueOf("2019-12-12"), income.getDatetime());
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
    public void incomeNonequalsWhenDateNotSame(){
        assertFalse(income.equals(income4));
    }
    
    @Test
    public void incomeNonequalWithDifferentType() {
        Object o = new Object();
        assertFalse(income.equals(o));
    }
         
}
