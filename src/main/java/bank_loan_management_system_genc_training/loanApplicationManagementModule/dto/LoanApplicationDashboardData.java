package bank_loan_management_system_genc_training.loanApplicationManagementModule.dto;

import java.util.List;

public class LoanApplicationDashboardData {

    private long totalApplications;
    private long pendingApplications;
    private long approvedApplications;
    private double approvedAmount;
    private List<LoanApplicationAdminDto> loanApplications;

    public long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(long totalApplications) {
        this.totalApplications = totalApplications;
    }

    public long getPendingApplications() {
        return pendingApplications;
    }

    public void setPendingApplications(long pendingApplications) {
        this.pendingApplications = pendingApplications;
    }

    public long getApprovedApplications() {
        return approvedApplications;
    }

    public void setApprovedApplications(long approvedApplications) {
        this.approvedApplications = approvedApplications;
    }

    public double getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(double approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    public List<LoanApplicationAdminDto> getLoanApplications() {
        return loanApplications;
    }

    public void setLoanApplications(List<LoanApplicationAdminDto> loanApplications) {
        this.loanApplications = loanApplications;
    }
}