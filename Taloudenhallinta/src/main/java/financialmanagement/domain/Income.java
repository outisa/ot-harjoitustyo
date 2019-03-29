/*
 * This class
 */
package financialmanagement.domain;

import java.sql.Date;

public class Income {
    private Integer id;
    private Date datetime;
    private Double amount;
    private String Category;
    private Integer userId;

    public Income(Integer id, Date datetime, Double amount, String Category, Integer userId) {
        this.id = id;
        this.datetime = datetime;
        this.amount = amount;
        this.Category = Category;
        this.userId = userId;
    }

    public Double getAmount() {
        return amount;
    }

    public String getCategory() {
        return Category;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setCategory(String Category) {
        this.Category = Category;
    }
    
    
}
