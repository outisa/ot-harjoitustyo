/*
 * This class represents a single expense.
 */
package financialmanagement.domain;

import java.sql.Date;
import java.time.LocalDateTime;

public class Expense {
    private Integer id;
    private LocalDateTime date;
    private Double amount;
    private String category;
    private Integer userId;
    private String place;

    public Expense(LocalDateTime date, Double amount, String category, Integer userId, String place){
        this.date = date;
        this.amount = amount;
        this.category = category;
        this.userId = userId;
        this.place = place;
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
    public boolean equals(Object o){
        if(! (o instanceof Expense)){
            return false;
        }
        Expense other = (Expense) o;
        if(this.date.equals(other.getDate())){
            if(this.amount.equals(other.getAmount())){
                if(this.category.equals(other.getCategory())){
                    if(this.userId.equals(other.getUserId())){
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
