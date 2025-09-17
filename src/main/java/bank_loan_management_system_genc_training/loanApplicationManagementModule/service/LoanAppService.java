package bank_loan_management_system_genc_training.loanApplicationManagementModule.service;

import bank_loan_management_system_genc_training.loanApplicationManagementModule.dto.IterableDTO;
import bank_loan_management_system_genc_training.loanApplicationManagementModule.dto.LoanApplicationDTO;
import bank_loan_management_system_genc_training.loanApplicationManagementModule.entity.LoanApplication;
import bank_loan_management_system_genc_training.loanApplicationManagementModule.entity.LoanApprovalStatus;

import java.util.Date;

public interface LoanAppService {

    LoanApplication save(LoanApplication loanApp);

    LoanApplication findById(Integer id);

    IterableDTO findAll();

    LoanApplicationDTO findByCustomerId(Integer customerId);

    LoanApplicationDTO findByLoanProductId(Integer loanProductId);

    LoanApplicationDTO findByApplicationDate(java.util.Date loanAppDate);

    LoanApplicationDTO findByLoanApprovalStatus(LoanApprovalStatus loanApprovalStatus);

    LoanApplication deleteById(Integer id);

}
