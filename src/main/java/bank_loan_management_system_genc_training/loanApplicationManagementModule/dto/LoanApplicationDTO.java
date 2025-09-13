package bank_loan_management_system_genc_training.loanApplicationManagementModule.dto;



import bank_loan_management_system_genc_training.loanApplicationManagementModule.entity.LoanApplication;

import java.util.HashSet;
import java.util.Set;

public class LoanApplicationDTO {

    private Set<LoanApplication> listOfLoanApplications = new HashSet<>();

    public Set<LoanApplication> getListOfLoanApplications() {
        return listOfLoanApplications;
    }

    public void setListOfLoanApplications(Set<LoanApplication> listOfLoanApplications) {
        this.listOfLoanApplications = listOfLoanApplications;
    }
}
