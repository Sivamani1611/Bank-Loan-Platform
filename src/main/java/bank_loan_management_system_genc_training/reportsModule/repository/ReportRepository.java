package bank_loan_management_system_genc_training.reportsModule.repository;

import bank_loan_management_system_genc_training.loanApplicationManagementModule.entity.LoanApplication;
import bank_loan_management_system_genc_training.loanApplicationManagementModule.entity.LoanApprovalStatus;
import bank_loan_management_system_genc_training.loanApplicationManagementModule.service.LoanApplicationService;
import bank_loan_management_system_genc_training.loanProductManagementModule.entity.LoanProduct;
import bank_loan_management_system_genc_training.loanProductManagementModule.service.LoanProductService;
import bank_loan_management_system_genc_training.reportsModule.dto.ReportDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.*;

@Repository
public class ReportRepository {

    @Autowired
    private LoanApplicationService loanAppService;

    @Autowired
    private LoanProductService loanProductService;

    public ReportDTO getDashboardData() {
        ReportDTO dto = new ReportDTO();

        // Ensure lists are not null
        dto.setLoanDistribution(new ArrayList<ReportDTO.LoanDistribution>());
        dto.setMonthlyPerformance(new ArrayList<ReportDTO.MonthlyPerformance>());

        // Get all loan applications
        List<LoanApplication> allApplications = (List<LoanApplication>) loanAppService.findAll().getLoanAppIterable();

        // ---------- Approval Rate ----------
        int totalApplications = allApplications.size();
        int approvedCount = 0;
        for (LoanApplication la : allApplications) {
            if (la.getApprovalStatus() == LoanApprovalStatus.APPROVED) {
                approvedCount++;
            }
        }
        if (totalApplications > 0) {
            dto.setApprovalRate((approvedCount * 100.0) / totalApplications);
        } else {
            dto.setApprovalRate(0.0);
        }

        // ---------- Loan Distribution ----------
        List<ReportDTO.LoanDistribution> loanDistributionList = new ArrayList<ReportDTO.LoanDistribution>();
        Iterable<LoanProduct> loanProductList = loanProductService.findAll().getLoanProduct();

        for (LoanProduct lp : loanProductList) {
            ReportDTO.LoanDistribution ld = new ReportDTO.LoanDistribution();
            ld.setProduct(lp.getProductName());
            double amount = 0.0;

            for (LoanApplication la : allApplications) {
                if (la.getApprovalStatus() == LoanApprovalStatus.APPROVED &&
                        la.getLoanProductId().equals(lp.getLoanProductId())) {
                    amount += la.getLoanAmount();
                }
            }
            ld.setAmount(amount);
            loanDistributionList.add(ld);
        }
        dto.setLoanDistribution(loanDistributionList);

        // ---------- Average Loan Amount (Approved only) ----------
        double totalApprovedAmount = 0.0;
        for (LoanApplication la : allApplications) {
            if (la.getApprovalStatus() == LoanApprovalStatus.APPROVED) {
                totalApprovedAmount += la.getLoanAmount();
            }
        }
        if (approvedCount > 0) {
            dto.setAvgLoanAmount(totalApprovedAmount / approvedCount);
        } else {
            dto.setAvgLoanAmount(0.0);
        }

        // ---------- Loan Counts ----------
        dto.setTotalApprovedLoans(approvedCount);

        int pendingCount = 0;
        for (LoanApplication la : allApplications) {
            if (la.getApprovalStatus() == LoanApprovalStatus.PENDING) {
                pendingCount++;
            }
        }
        dto.setTotalPendingLoans(pendingCount);

        // ---------- Active Customers ----------
        Set<Integer> activeCustomers = new HashSet<Integer>();
        for (LoanApplication la : allApplications) {
            if (la.getApprovalStatus() == LoanApprovalStatus.APPROVED) {
                activeCustomers.add(la.getCustomerId());
            }
        }
        dto.setActiveCustomers(activeCustomers.size());

        // ---------- Active Products ----------
        Set<Integer> activeProducts = new HashSet<Integer>();
        for (LoanApplication la : allApplications) {
            if (la.getApprovalStatus() == LoanApprovalStatus.APPROVED) {
                activeProducts.add(la.getLoanProductId());
            }
        }
        dto.setActiveProducts(activeProducts.size());

        // ---------- Monthly Performance (last 6 months) ----------
        List<ReportDTO.MonthlyPerformance> monthlyPerformanceList = new ArrayList<ReportDTO.MonthlyPerformance>();
        YearMonth currentMonth = YearMonth.now();

        for (int i = 0; i < 6; i++) {
            YearMonth month = currentMonth.minusMonths(i);

            int applications = 0;
            int approved = 0;
            double approvedAmount = 0.0;

            for (LoanApplication la : allApplications) {
                Date rawDate = la.getApplicationDate(); // java.util.Date
                if (rawDate == null) {
                    continue;
                }
                LocalDate appDate = new java.sql.Date(rawDate.getTime()).toLocalDate();

                if (appDate.getMonthValue() == month.getMonthValue() &&
                        appDate.getYear() == month.getYear()) {

                    applications++;

                    if (la.getApprovalStatus() == LoanApprovalStatus.APPROVED) {
                        approved++;
                        approvedAmount += la.getLoanAmount();
                    }
                }
            }

            ReportDTO.MonthlyPerformance mp = new ReportDTO.MonthlyPerformance();
            mp.setMonth(month.getMonth().name() + " " + month.getYear());
            mp.setApplications(applications);
            mp.setApproved(approved);

            if (applications > 0) {
                double rate = (approved * 100.0) / applications;
                mp.setApprovalRate(String.format("%.2f%%", rate));
            } else {
                mp.setApprovalRate("0%");
            }

            mp.setAmount(approvedAmount);
            monthlyPerformanceList.add(mp);
        }

        dto.setMonthlyPerformance(monthlyPerformanceList);

        return dto;
    }
}