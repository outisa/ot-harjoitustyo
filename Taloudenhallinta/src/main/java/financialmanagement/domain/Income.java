
package financialmanagement.domain;

import java.sql.Date;
/**
 * This class represents a singular Income.
 * @author ousavola
 */
public class Income {
    private Date datetime;
    private Double amount;
    private String category;
    private Integer userId;

    /**
     * Constructor creates new income.
     * @param userId id from the current user
     * @param datetime date, when income was paid
     * @param category name of the category
     * @param amount decimal number between 0 and 9999999.99
     */
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

    /**
     * Investigates whether given object equals with Income or not.
     * @param object 
     * @return true, if object equals with Income; false, if object is not instance of Income
     * or it does not equals with Income.
     */
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Income)) {
            return false;
        }
        Income other = (Income) object;
        return this.datetime.equals(other.getDatetime()) && this.userId.equals(other.getUserId()) && (this.category.equals(other.getCategory()) && this.amount.equals(other.getAmount()));
    }
       
}
