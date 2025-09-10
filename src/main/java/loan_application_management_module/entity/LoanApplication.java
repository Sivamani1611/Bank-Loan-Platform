package loan_application_management_module.entity;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
public class LoanApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer applicationId;

    private Integer customerId;

    private Integer loanProductId;

    private Double loanAmount;

    private Date applicationDate;

    @Enumerated(EnumType.STRING)
    private LoanApprovalStatus approvalStatus;


    public Integer getApplicationId() {
        return applicationId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getLoanProductId() {
        return loanProductId;
    }

    public void setLoanProductId(Integer loanProductId) {
        this.loanProductId = loanProductId;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public LoanApprovalStatus getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(LoanApprovalStatus approvalStatus) {
        this.approvalStatus = approvalStatus;
    }
}
