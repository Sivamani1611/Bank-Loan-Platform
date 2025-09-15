package bank_loan_management_system_genc_training.repaymentManagementModule.dto;

import java.math.BigDecimal;

public class PaymentRequestDTO {
    private BigDecimal paidAmount;

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }
}