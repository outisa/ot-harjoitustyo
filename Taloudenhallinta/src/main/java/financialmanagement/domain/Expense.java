/*
 * This class represents a single expense.
 */
package financialmanagement.domain;

import java.time.LocalDateTime;

public class Expense {
    private Integer id;
    private LocalDateTime date;
    private Double amount;
    private String category;
    private Integer userId;
    private String place;

    public Expense(LocalDateTime date, Double amount, String category, Integer userId) {
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

    public LocalDateTime getDate() {
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
