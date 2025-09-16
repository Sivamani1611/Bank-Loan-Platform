package bank_loan_management_system_genc_training.loanApplicationManagementModule.service;

import bank_loan_management_system_genc_training.customerModule.entity.Customer;
import bank_loan_management_system_genc_training.customerModule.repository.CustomerRepository;
import bank_loan_management_system_genc_training.loanApplicationManagementModule.dto.LoanApplicationAdminDto;
import bank_loan_management_system_genc_training.loanApplicationManagementModule.dto.LoanApplicationDashboardData;
import bank_loan_management_system_genc_training.loanApplicationManagementModule.entity.LoanApplication;
import bank_loan_management_system_genc_training.loanApplicationManagementModule.entity.LoanApprovalStatus;
import bank_loan_management_system_genc_training.loanApplicationManagementModule.repository.LoanApplicationRepository;
import bank_loan_management_system_genc_training.loanProductManagementModule.entity.LoanProduct;
import bank_loan_management_system_genc_training.loanProductManagementModule.repository.LoanProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class LoanApplicationAdminService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoanProductRepository loanProductRepository;

    public LoanApplicationDashboardData getAdminDashboardData() {
        LoanApplicationDashboardData dashboardData = new LoanApplicationDashboardData();
        List<LoanApplicationAdminDto> adminApplications = new ArrayList<>();

        List<LoanApplication> allLoans = StreamSupport.stream(loanApplicationRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());

        Map<Integer, Customer> customersById = StreamSupport.stream(customerRepository.findAll().spliterator(), false)
                .collect(Collectors.toMap(Customer::getCustomerId, customer -> customer));

        Map<Integer, LoanProduct> productsById = StreamSupport.stream(loanProductRepository.findAll().spliterator(), false)
                .collect(Collectors.toMap(LoanProduct::getLoanProductId, product -> product));

        long totalApplications = allLoans.size();
        long pendingApplications = 0;
        long approvedApplications = 0;
        double approvedAmount = 0.0;

        for (LoanApplication loan : allLoans) {
            LoanApplicationAdminDto dto = new LoanApplicationAdminDto();
            dto.setApplicationId(loan.getApplicationId());
            dto.setLoanAmount(loan.getLoanAmount());
            dto.setAppliedDate(loan.getApplicationDate());
            dto.setStatus(loan.getApprovalStatus() != null ? loan.getApprovalStatus().name() : "UNKNOWN");

            Customer customer = customersById.get(loan.getCustomerId());
            if (customer != null) {
                dto.setCustomerName(customer.getName());
                dto.setCustomerEmail(customer.getEmail());
            }

            LoanProduct product = productsById.get(loan.getLoanProductId());
            if (product != null) {
                dto.setLoanType(product.getProductName());
                double emi = (loan.getLoanAmount() != null && product.getInterestRate() != null && product.getTenure() != null)
                        ? (loan.getLoanAmount() * product.getInterestRate() / 1200) / (1 - Math.pow(1 + product.getInterestRate() / 1200, -product.getTenure()))
                        : 0.0;
                dto.setEmi(emi);
            }

            adminApplications.add(dto);

            if (loan.getApprovalStatus() == LoanApprovalStatus.PENDING) {
                pendingApplications++;
            } else if (loan.getApprovalStatus() == LoanApprovalStatus.APPROVED) {
                approvedApplications++;
                if (loan.getLoanAmount() != null) {
                    approvedAmount += loan.getLoanAmount();
                }
            }
        }

        dashboardData.setTotalApplications(totalApplications);
        dashboardData.setPendingApplications(pendingApplications);
        dashboardData.setApprovedApplications(approvedApplications);
        dashboardData.setApprovedAmount(approvedAmount);
        dashboardData.setLoanApplications(adminApplications);

        return dashboardData;
    }

    public LoanApplication updateLoanStatus(Integer applicationId, LoanApprovalStatus newStatus) {
        Optional<LoanApplication> loanAppOpt = loanApplicationRepository.findById(applicationId);

        if (loanAppOpt.isPresent()) {
            LoanApplication loanApp = loanAppOpt.get();
            loanApp.setApprovalStatus(newStatus);
            return loanApplicationRepository.save(loanApp);
        }

        return null;
    }
}