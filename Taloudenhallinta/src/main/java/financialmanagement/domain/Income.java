/*
 * This class
 */
package financialmanagement.domain;

import java.sql.Date;
import java.time.LocalDateTime;

public class Income {
    private Integer id;
    private Date datetime;
    private Double amount;
    private String category;
    private Integer userId;

    public Income(Integer userId, Date datetime, String category, Double amount) {
        this.userId = userId;
        this.datetime = datetime;
        this.category = category;
        this.amount = amount;
    }

    public Double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public Date getDatetime() {
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
