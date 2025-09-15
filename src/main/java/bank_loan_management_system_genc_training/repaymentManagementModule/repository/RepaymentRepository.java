package bank_loan_management_system_genc_training.repaymentManagementModule.repository;

import bank_loan_management_system_genc_training.repaymentManagementModule.entity.Repayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RepaymentRepository extends JpaRepository<Repayment, Integer> {

    // This method is now correctly defined to find repayments by the LoanApplication's
    // primary key, which is an Integer.
    List<Repayment> findByLoanApplicationApplicationId(Integer applicationId);
}