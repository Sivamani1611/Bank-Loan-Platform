package bank_loan_management_system_genc_training.loanProductManagementModule.repository;

import bank_loan_management_system_genc_training.loanProductManagementModule.entity.LoanProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoanProductRepository extends JpaRepository<LoanProduct, Integer> {

     Optional<LoanProduct> findByLoanProductId(Integer loanProductId);
}
