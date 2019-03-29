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

    public Expense(Integer id, Date date, Double amount, String category, Integer userId) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.userId = userId;
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

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    
    
}
