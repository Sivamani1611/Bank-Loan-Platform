package bank_loan_management_system_genc_training.loanProductManagementModule.dto;


import bank_loan_management_system_genc_training.loanProductManagementModule.entity.LoanProduct;

public class LoanProductDTO {

    private Iterable<LoanProduct> loanProduct;

    public Iterable<LoanProduct> getLoanProduct() {
        return loanProduct;
    }

    public void setLoanProduct(Iterable<LoanProduct> loanProduct) {
        this.loanProduct = loanProduct;
    }
}
