/*
 * This class represents a single expense.
 */
package financialmanagement.domain;

import java.sql.Date;

public class Expense {
    private Date date;
    private Double amount;
    private String category;
    private Integer userId;

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
