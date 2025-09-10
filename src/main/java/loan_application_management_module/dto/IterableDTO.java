package loan_application_management_module.dto;


import loan_application_management_module.entity.LoanApplication;

public class IterableDTO {
    private Iterable<LoanApplication> loanAppIterable;

    public Iterable<LoanApplication> getLoanAppIterable() {
        return loanAppIterable;
    }

    public void setLoanAppIterable(Iterable<LoanApplication> loanAppIterable) {
        this.loanAppIterable = loanAppIterable;
    }
}
