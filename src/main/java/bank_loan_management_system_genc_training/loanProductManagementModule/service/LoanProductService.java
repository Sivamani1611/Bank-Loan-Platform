package bank_loan_management_system_genc_training.loanProductManagementModule.service;

import bank_loan_management_system_genc_training.loanProductManagementModule.dto.LoanProductDTO;
import bank_loan_management_system_genc_training.loanProductManagementModule.entity.LoanProduct;

public interface LoanProductService {

    LoanProduct save(LoanProduct loanProduct);

    void deleteByLoanProductId(Integer loanProductId);

    LoanProductDTO findAll();

    LoanProduct findByLoanProductId(Integer loanProductId);

    LoanProduct updateLoanProduct(Integer loanProductId, LoanProduct updatedLoanProduct);
}
