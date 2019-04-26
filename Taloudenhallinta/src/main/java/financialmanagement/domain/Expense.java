
package financialmanagement.domain;

import java.sql.Date;
/**
 * This class represents a single expense.
 */
public class Expense {
    private Date date;
    private Double amount;
    private String category;
    private Integer userId;

    /**
     * Constructor creates new Expense.
     * 
     * @param userId id from the current user.
     * @param date date, when expense was paid.
     * @param category name of the category from the given list
     * @param amount decimal number between 0.0 and 9999999.99 in form xxx.xx
     */
    public Expense(Integer userId, Date date, String category, Double amount) {
        this.userId = userId;
        this.date = date;
        this.category = category;
        this.amount = amount;
    } 

    public Integer getUserId() {
        return userId;
    }

    public Double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public Date getDate() {
        return date;
    }
   
    /**
     * Investigates whether given object equals with expense or not.
     * 
     * @param object
     * @return true, if object equals with Expense; false, if object is not instance of Expense
     * or it does not equals with expense.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Expense)) {
            return false;
        }
        Expense other = (Expense) object;
        return this.date.equals(other.getDate()) && this.category.equals(other.getCategory()) && this.userId.equals(other.getUserId()) && this.amount.equals(other.getAmount());
    }
}
