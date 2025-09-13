package bank_loan_management_system_genc_training.loanApplicationManagementModule.repository;

import bank_loan_management_system_genc_training.loanApplicationManagementModule.entity.LoanApplication;
import bank_loan_management_system_genc_training.loanApplicationManagementModule.entity.LoanApprovalStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Set;

public interface LoanApplicationRepository extends CrudRepository<LoanApplication, Integer> {

    Set<LoanApplication> findByCustomerId(Integer customerId);

    Set<LoanApplication> findByApplicationDate(Date loanAppDate);

    Set<LoanApplication> findByApprovalStatus(LoanApprovalStatus approvalStatus);

    Set<LoanApplication> findByLoanProductId(Integer id);
}
