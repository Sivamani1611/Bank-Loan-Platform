package bank_loan_management_system_genc_training.repaymentManagementModule.service;

import bank_loan_management_system_genc_training.repaymentManagementModule.dto.RepaymentGenerationRequestDTO;
import bank_loan_management_system_genc_training.repaymentManagementModule.dto.RepaymentResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface RepaymentService {

    void generateRepaymentSchedule(RepaymentGenerationRequestDTO request);

    void makePayment(Integer repaymentId, BigDecimal paidAmount);

    BigDecimal getOutstandingBalance(Integer applicationId);

    List<RepaymentResponseDTO> getRepaymentSchedule(Integer applicationId);

}
