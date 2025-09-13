package bank_loan_management_system_genc_training.loanApplicationManagementModule.dto;


import bank_loan_management_system_genc_training.loanApplicationManagementModule.entity.LoanApplication;

public class IterableDTO {
    private Iterable<LoanApplication> loanAppIterable;

    public Iterable<LoanApplication> getLoanAppIterable() {
        return loanAppIterable;
    }

    public void setLoanAppIterable(Iterable<LoanApplication> loanAppIterable) {
        this.loanAppIterable = loanAppIterable;
    }
}
