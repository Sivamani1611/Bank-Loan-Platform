package bank_loan_management_system_genc_training.reportsModule.repository;




import bank_loan_management_system_genc_training.loanProductManagementModule.service.LoanProductServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;



@Repository
public class ReportRepository {
    @Autowired
    private LoanProductServiceImpl loanProductServiceImpl;
}
