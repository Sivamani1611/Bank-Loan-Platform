package bank_loan_management_system_genc_training.loanProductManagementModule.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class LoanProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer loanProductId;

    private String productName;

    private Double intrestRate;

    private Double minAmount;

    private Double maxAmount;

    private Integer tenure;


    public Integer getLoanProductId() {
        return loanProductId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getIntrestRate() {
        return intrestRate;
    }

    public void setIntrestRate(Double intrestRate) {
        this.intrestRate = intrestRate;
    }

    public Double getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
    }

    public Double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public Integer getTenure() {
        return tenure;
    }

    public void setTenure(Integer tenure) {
        this.tenure = tenure;
    }
}
