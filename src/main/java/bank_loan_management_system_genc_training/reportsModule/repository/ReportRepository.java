package bank_loan_management_system_genc_training.reportsModule.repository;

import bank_loan_management_system_genc_training.loanApplicationManagementModule.service.LoanApplicationService;
import bank_loan_management_system_genc_training.loanProductManagementModule.service.LoanProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class ReportRepository {

    @Autowired
    private LoanApplicationService loanAppService;

    @Autowired
    private LoanProductService loanProductService;

<<<<<<< Updated upstream
=======

    public ReportDTO getDashboardData() {
        ReportDTO dto = new ReportDTO();
        // Initialize lists to ensure they are never null
        dto.setLoanDistribution(new ArrayList<>());
        dto.setMonthlyPerformance(new ArrayList<>());

        try (Connection conn = getConnection()) {

            // Approval Rate
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(
                         "SELECT (SUM(CASE WHEN approval_status='APPROVED' THEN 1 ELSE 0 END) / COUNT(*)) * 100 AS approvalRate FROM loan_application"
                 )) {
                if (rs.next()) {
                    dto.setApprovalRate(rs.getDouble("approvalRate"));
                }
            }


            List<ReportDTO.LoanDistribution> loanDistributionList = new ArrayList<>();

            Set<LoanApplication> loanApplicationList = loanAppService.findByLoanApprovalStatus(LoanApprovalStatus.APPROVED).getListOfLoanApplications();
            Iterable<LoanProduct> loanProductList = loanProductService.findAll().getLoanProduct();
            for (LoanProduct lp : loanProductList){
                ReportDTO.LoanDistribution ld = new ReportDTO.LoanDistribution();
                ld.setProduct(lp.getProductName());
                Integer productId = lp.getLoanProductId();
                double amount = 0.0;
                for (LoanApplication la : loanApplicationList) {
                    if (la.getLoanProductId().equals(productId)) {
                        amount += la.getLoanAmount();
                    }
                }
                ld.setAmount(amount);
                loanDistributionList.add(ld);
            }
            dto.setLoanDistribution(loanDistributionList);







            // Average Loan Amount (approved)
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(
                         "SELECT AVG(loan_amount) AS avgloan_amount FROM loan_application WHERE approval_status='APPROVED'"
                 )) {
                if (rs.next()) {
                    dto.setAvgLoanAmount(rs.getDouble("avgloan_amount"));
                }
            }

            // Total Approved Loans
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(
                         "SELECT COUNT(*) AS totalApprovedLoans FROM loan_application WHERE approval_status='APPROVED'"
                 )) {
                if (rs.next()) {
                    dto.setTotalApprovedLoans(rs.getInt("totalApprovedLoans"));
                }
            }

            // Total Pending Loans
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(
                         "SELECT COUNT(*) AS totalPendingLoans FROM loan_application WHERE approval_status='PENDING'"
                 )) {
                if (rs.next()) {
                    dto.setTotalPendingLoans(rs.getInt("totalPendingLoans"));
                }
            }

            // Total Repayments Made (COMPLETED)
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(
                         "SELECT COUNT(*) AS totalRepaymentsMade FROM repayment WHERE payment_status='COMPLETED'"
                 )) {
                if (rs.next()) {
                    dto.setTotalRepaymentsMade(rs.getInt("totalRepaymentsMade"));
                }
            }

            // Remaining Repayments (PENDING)
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(
                         "SELECT COUNT(*) AS remainingRepayments FROM repayment WHERE payment_status='PENDING'"
                 )) {
                if (rs.next()) {
                    dto.setRemainingRepayments(rs.getInt("remainingRepayments"));
                }
            }

            // Active Customers (with at least one approved loan)
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(
                         "SELECT COUNT(DISTINCT customer_id) AS activeCustomers FROM loan_application WHERE approval_status='APPROVED'"
                 )) {
                if (rs.next()) {
                    dto.setActiveCustomers(rs.getInt("activeCustomers"));
                }
            }

            // Active Products (with at least one approved loan)
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(
                         "SELECT COUNT(DISTINCT loan_product_id) AS activeProducts FROM loan_application WHERE approval_status='APPROVED'"
                 )) {
                if (rs.next()) {
                    dto.setActiveProducts(rs.getInt("activeProducts"));
                }
            }

            // Loan Distribution (by product)
//            List<ReportDTO.LoanDistribution> loanDistributionList = new ArrayList<>();

//            Set<LoanApplication> loanApplicationList = loanAppService.findByLoanApprovalStatus(LoanApprovalStatus.APPROVED).getListOfLoanApplications();
//            Iterable<LoanProduct> loanProductList = loanProductService.findAll().getLoanProduct();
            for (LoanProduct lp : loanProductList){
                ReportDTO.LoanDistribution ld = new ReportDTO.LoanDistribution();
                ld.setProduct(lp.getProductName());
                Integer productId = lp.getLoanProductId();
                double amount = 0.0;
                for (LoanApplication la : loanApplicationList) {
                    if (la.getLoanProductId().equals(productId)) {
                        amount += la.getLoanAmount();
                    }
                }
                ld.setAmount(amount);
                loanDistributionList.add(ld);
            }
            dto.setLoanDistribution(loanDistributionList);

            // Monthly Performance (last 6 months)
            List<ReportDTO.MonthlyPerformance> monthlyPerformanceList = new ArrayList<>();
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(
                         "SELECT DATE_FORMAT(application_date, '%b %Y') AS month, " +
                                 "COUNT(*) AS applications, " +
                                 "SUM(CASE WHEN approval_status='APPROVED' THEN 1 ELSE 0 END) AS approved, " +
                                 "CONCAT(ROUND((SUM(CASE WHEN approval_status='APPROVED' THEN 1 ELSE 0 END)/COUNT(*))*100), '%') AS approvalRate, " +
                                 "SUM(CASE WHEN approval_status='APPROVED' THEN loan_amount ELSE 0 END) AS amount " +
                                 "FROM loan_application " +
                                 "GROUP BY month " +
                                 "ORDER BY MIN(application_date) DESC LIMIT 6"
                 )) {
                while (rs.next()) {
                    ReportDTO.MonthlyPerformance mp = new ReportDTO.MonthlyPerformance();
                    mp.setMonth(rs.getString("month"));
                    mp.setApplications(rs.getInt("applications"));
                    mp.setApproved(rs.getInt("approved"));
                    mp.setApprovalRate(rs.getString("approvalRate"));
                    mp.setAmount(rs.getDouble("amount"));
                    monthlyPerformanceList.add(mp);
                }
            }
            dto.setMonthlyPerformance(monthlyPerformanceList);

        } catch (SQLException e) {
            System.err.println("Database error while fetching dashboard data.");
            e.printStackTrace();
            // Re-throw as a RuntimeException to be handled by the service/controller layer
            throw new RuntimeException("Error fetching dashboard data from the database", e);
        }
        return dto;
    }

    private Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
>>>>>>> Stashed changes
}