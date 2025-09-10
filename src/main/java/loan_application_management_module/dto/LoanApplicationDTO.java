package loan_application_management_module.dto;


import loan_application_management_module.entity.LoanApplication;

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
