/*
 * This class represents a single expense.
 */
package financialmanagement.domain;

import java.sql.Date;

public class Expense {
    private Integer id;
    private Date date;
    private Double amount;
    private String category;
    private Integer userId;
    private String place;

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
   
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Expense)) {
            return false;
        }
        Expense other = (Expense) o;
        return this.date.equals(other.getDate()) && this.category.equals(other.getCategory()) && this.userId.equals(other.getUserId()) && this.amount.equals(other.getAmount());
    }
}
