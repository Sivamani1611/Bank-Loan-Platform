package loan_application_management_module.repository;

import loan_application_management_module.entity.LoanApplication;
import loan_application_management_module.entity.LoanApprovalStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Set;

public interface LoanApplicationRepository extends CrudRepository<LoanApplication, Integer> {

    Set<LoanApplication> findByCustomerId(Integer customerId);

    Set<LoanApplication> findByApplicationDate(Date loanAppDate);

    Set<LoanApplication> findByApprovalStatus(LoanApprovalStatus approvalStatus);

    Set<LoanApplication> findByLoanProductId(Integer id);
}
