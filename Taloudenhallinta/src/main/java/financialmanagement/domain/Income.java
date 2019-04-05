/*
 * This class
 */
package financialmanagement.domain;

import java.time.LocalDateTime;

public class Income {
    private Integer id;
    private LocalDateTime datetime;
    private Double amount;
    private String category;
    private Integer userId;

    public Income(LocalDateTime datetime, Double amount, String category, Integer userId) {
        this.datetime = datetime;
        this.amount = amount;
        this.category = category;
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public Integer getUserId() {
        return userId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Income)) {
            return false;
        }
        Income other = (Income) o;
        return this.datetime.equals(other.getDatetime()) && this.userId.equals(other.getUserId()) && (this.category.equals(other.getCategory()) && this.amount.equals(other.getAmount()));
    }
       
}
