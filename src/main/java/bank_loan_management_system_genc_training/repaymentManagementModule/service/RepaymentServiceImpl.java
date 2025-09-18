package bank_loan_management_system_genc_training.repaymentManagementModule.service;

import bank_loan_management_system_genc_training.loanApplicationManagementModule.entity.LoanApplication;
import bank_loan_management_system_genc_training.loanApplicationManagementModule.entity.LoanApprovalStatus;
import bank_loan_management_system_genc_training.loanApplicationManagementModule.repository.LoanApplicationRepository;
import bank_loan_management_system_genc_training.loanProductManagementModule.repository.LoanProductRepository;
import bank_loan_management_system_genc_training.repaymentManagementModule.dto.RepaymentGenerationRequestDTO;
import bank_loan_management_system_genc_training.repaymentManagementModule.dto.RepaymentResponseDTO;
import bank_loan_management_system_genc_training.repaymentManagementModule.entity.Repayment;
import bank_loan_management_system_genc_training.repaymentManagementModule.repository.RepaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RepaymentServiceImpl implements RepaymentService {

    @Autowired
    private RepaymentRepository repaymentRepository;

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private LoanProductRepository loanProductRepository;

    @Transactional
    public void generateRepaymentSchedule(RepaymentGenerationRequestDTO request) {
        // Step 1: Check loan application status
        LoanApplication loanApplication = loanApplicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new IllegalArgumentException("Loan application not found."));

        if (loanApplication.getApprovalStatus() != LoanApprovalStatus.APPROVED) {
            throw new IllegalStateException("Loan application is not yet approved. Status: " + loanApplication.getApprovalStatus());
        }

        // Step 2: Proceed with generating the schedule (existing logic)
        BigDecimal principal = request.getLoanAmount();
        BigDecimal interestRate = request.getInterestRate();
        Integer tenureInMonths = request.getTenure();

        BigDecimal monthlyInterestRate = interestRate.divide(BigDecimal.valueOf(1200), 10, RoundingMode.HALF_UP);
        BigDecimal emi = principal
                .multiply(monthlyInterestRate)
                .divide(BigDecimal.ONE.subtract(BigDecimal.ONE.divide(monthlyInterestRate.add(BigDecimal.ONE).pow(tenureInMonths), 10, RoundingMode.HALF_UP)), 2, RoundingMode.HALF_UP);

        LocalDate nextDueDate = LocalDate.now().plusMonths(1);
        BigDecimal remainingPrincipal = principal;

        for (int i = 0; i < tenureInMonths; i++) {
            BigDecimal interestComponent = remainingPrincipal.multiply(monthlyInterestRate);
            BigDecimal principalComponent = emi.subtract(interestComponent);
            remainingPrincipal = remainingPrincipal.subtract(principalComponent);

            Repayment repayment = new Repayment();
            repayment.setLoanApplication(loanApplication);
            repayment.setDueDate(nextDueDate);
            repayment.setAmountDue(emi);
            repayment.setPaymentStatus(Repayment.PaymentStatus.PENDING);
            repaymentRepository.save(repayment);

            nextDueDate = nextDueDate.plusMonths(1);
        }

    }

    @Transactional
    public void makePayment(Integer repaymentId, BigDecimal paidAmount) {
        Optional<Repayment> optionalRepayment = repaymentRepository.findById(repaymentId);

        if (optionalRepayment.isPresent()) {
            Repayment repayment = optionalRepayment.get();

            if (repayment.getPaymentStatus() == Repayment.PaymentStatus.COMPLETED) {
                throw new IllegalStateException("This payment has already been completed.");
            }

            if (paidAmount.compareTo(repayment.getAmountDue()) >= 0) {
                repayment.setPaymentStatus(Repayment.PaymentStatus.COMPLETED);
                repayment.setPaymentDate(LocalDate.now());
                repaymentRepository.save(repayment);
            } else {
                throw new IllegalArgumentException("Paid amount is less than the amount due.");
            }
        } else {
            throw new IllegalArgumentException("Repayment entry not found with ID: " + repaymentId);
        }
    }

    public BigDecimal getOutstandingBalance(Integer applicationId) {
        List<Repayment> repayments = repaymentRepository.findByLoanApplicationApplicationId(applicationId);

        BigDecimal outstandingBalance = BigDecimal.ZERO;
        for (Repayment repayment : repayments) {
            if (repayment.getPaymentStatus() == Repayment.PaymentStatus.PENDING) {
                outstandingBalance = outstandingBalance.add(repayment.getAmountDue());
            }
        }
        return outstandingBalance;
    }

    /**
     * Retrieves the full repayment schedule for a loan application and maps it to a DTO.
     * @param applicationId The ID of the loan application.
     * @return A list of all repayment entries for the loan.
     */
    public List<RepaymentResponseDTO> getRepaymentSchedule(Integer applicationId) {
        List<Repayment> repayments = repaymentRepository.findByLoanApplicationApplicationId(applicationId);

        return repayments.stream()
                .map(repayment -> {
                    RepaymentResponseDTO dto = new RepaymentResponseDTO();
                    dto.setRepaymentId(repayment.getRepaymentId());
                    dto.setDueDate(repayment.getDueDate());
                    dto.setAmountDue(repayment.getAmountDue());
                    dto.setPaymentDate(repayment.getPaymentDate());
                    dto.setPaymentStatus(repayment.getPaymentStatus().name());

                    // Fetch and set the loan product name
                    loanProductRepository.findById(repayment.getLoanApplication().getLoanProductId())
                            .ifPresent(loanProduct -> dto.setLoanProductName(loanProduct.getProductName()));

                    return dto;
                })
                .collect(Collectors.toList());
    }

}