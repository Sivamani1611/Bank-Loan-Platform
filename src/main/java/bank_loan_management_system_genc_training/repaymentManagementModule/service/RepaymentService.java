package bank_loan_management_system_genc_training.repaymentManagementModule.service;

import bank_loan_management_system_genc_training.loanApplicationManagementModule.entity.LoanApplication;
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
public class RepaymentService {

    @Autowired
    private RepaymentRepository repaymentRepository;

    @Autowired
    private LoanApplicationRepository loanApplicationRepository;

    @Autowired
    private LoanProductRepository loanProductRepository;

    @Transactional
    public void generateRepaymentSchedule(RepaymentGenerationRequestDTO requestDTO) {
        Optional<LoanApplication> optionalLoan = loanApplicationRepository.findById(requestDTO.getApplicationId());

        if (optionalLoan.isPresent()) {
            LoanApplication loan = optionalLoan.get();

            // Check if a schedule already exists
            List<Repayment> existingSchedule = repaymentRepository.findByLoanApplicationApplicationId(requestDTO.getApplicationId());
            if (!existingSchedule.isEmpty()) {
                throw new IllegalStateException("Repayment schedule for this loan already exists.");
            }

            BigDecimal loanAmount = requestDTO.getLoanAmount();
            BigDecimal interestRate = requestDTO.getInterestRate();
            Integer tenure = requestDTO.getTenure();

            // Calculate total amount with simple interest
            BigDecimal totalAmount = loanAmount.add(loanAmount.multiply(interestRate).divide(new BigDecimal(100), 2, RoundingMode.HALF_UP));

            // Calculate monthly installment
            BigDecimal monthlyDue = totalAmount.divide(new BigDecimal(tenure), 2, RoundingMode.HALF_UP);

            // Use the loan application date to determine the first due date
            LocalDate applicationDate = loan.getApplicationDate().toLocalDate();
            LocalDate firstDueDate = applicationDate.plusMonths(1);

            for (int i = 0; i < tenure; i++) {
                Repayment repayment = new Repayment();
                repayment.setLoanApplication(loan);
                repayment.setAmountDue(monthlyDue);
                repayment.setDueDate(firstDueDate.plusMonths(i)); // Correctly increments by month
                repayment.setPaymentStatus(Repayment.PaymentStatus.PENDING);

                repaymentRepository.save(repayment);
            }
        } else {
            throw new IllegalArgumentException("Loan Application not found with ID: " + requestDTO.getApplicationId());
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