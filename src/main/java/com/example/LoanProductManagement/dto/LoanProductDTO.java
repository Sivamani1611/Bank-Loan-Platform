package com.example.LoanProductManagement.dto;

import com.example.LoanProductManagement.entity.LoanProduct;

public class LoanProductDTO {

    private Iterable<LoanProduct> loanProduct;

    public Iterable<LoanProduct> getLoanProduct() {
        return loanProduct;
    }

    public void setLoanProduct(Iterable<LoanProduct> loanProduct) {
        this.loanProduct = loanProduct;
    }
}
