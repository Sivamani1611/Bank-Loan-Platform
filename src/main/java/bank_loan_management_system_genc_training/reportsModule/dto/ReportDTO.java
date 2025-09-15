package bank_loan_management_system_genc_training.reportsModule.dto;

import java.util.List;

public class ReportDTO {
    private double approvalRate;
    private double avgLoanAmount;
    private int totalApprovedLoans;
    private int totalPendingLoans;
    private int totalRepaymentsMade;
    private int remainingRepayments;
    private int activeCustomers;
    private int activeProducts;
    private List<LoanDistribution> loanDistribution;
    private List<MonthlyPerformance> monthlyPerformance;

    // Getters and setters

    public double getApprovalRate() { return approvalRate; }
    public void setApprovalRate(double approvalRate) { this.approvalRate = approvalRate; }

    public double getAvgLoanAmount() { return avgLoanAmount; }
    public void setAvgLoanAmount(double avgLoanAmount) { this.avgLoanAmount = avgLoanAmount; }

    public int getTotalApprovedLoans() { return totalApprovedLoans; }
    public void setTotalApprovedLoans(int totalApprovedLoans) { this.totalApprovedLoans = totalApprovedLoans; }

    public int getTotalPendingLoans() { return totalPendingLoans; }
    public void setTotalPendingLoans(int totalPendingLoans) { this.totalPendingLoans = totalPendingLoans; }

    public int getTotalRepaymentsMade() { return totalRepaymentsMade; }
    public void setTotalRepaymentsMade(int totalRepaymentsMade) { this.totalRepaymentsMade = totalRepaymentsMade; }

    public int getRemainingRepayments() { return remainingRepayments; }
    public void setRemainingRepayments(int remainingRepayments) { this.remainingRepayments = remainingRepayments; }

    public int getActiveCustomers() { return activeCustomers; }
    public void setActiveCustomers(int activeCustomers) { this.activeCustomers = activeCustomers; }

    public int getActiveProducts() { return activeProducts; }
    public void setActiveProducts(int activeProducts) { this.activeProducts = activeProducts; }

    public List<LoanDistribution> getLoanDistribution() { return loanDistribution; }
    public void setLoanDistribution(List<LoanDistribution> loanDistribution) { this.loanDistribution = loanDistribution; }

    public List<MonthlyPerformance> getMonthlyPerformance() { return monthlyPerformance; }
    public void setMonthlyPerformance(List<MonthlyPerformance> monthlyPerformance) { this.monthlyPerformance = monthlyPerformance; }

    public static class LoanDistribution {
        private String product;
        private double amount;

        public String getProduct() { return product; }
        public void setProduct(String product) { this.product = product; }

        public double getAmount() { return amount; }
        public void setAmount(double amount) { this.amount = amount; }
    }

    public static class MonthlyPerformance {
        private String month;
        private int applications;
        private int approved;
        private String approvalRate;
        private double amount;

        public String getMonth() { return month; }
        public void setMonth(String month) { this.month = month; }

        public int getApplications() { return applications; }
        public void setApplications(int applications) { this.applications = applications; }

        public int getApproved() { return approved; }
        public void setApproved(int approved) { this.approved = approved; }

        public String getApprovalRate() { return approvalRate; }
        public void setApprovalRate(String approvalRate) { this.approvalRate = approvalRate; }

        public double getAmount() { return amount; }
        public void setAmount(double amount) { this.amount = amount; }
    }
}
