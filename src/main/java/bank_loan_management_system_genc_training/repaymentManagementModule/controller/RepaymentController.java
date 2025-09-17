package bank_loan_management_system_genc_training.repaymentManagementModule.controller;

import bank_loan_management_system_genc_training.repaymentManagementModule.dto.PaymentRequestDTO;
import bank_loan_management_system_genc_training.repaymentManagementModule.dto.RepaymentGenerationRequestDTO;
import bank_loan_management_system_genc_training.repaymentManagementModule.dto.RepaymentResponseDTO;
import bank_loan_management_system_genc_training.repaymentManagementModule.service.RepaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/repayments")
public class RepaymentController {

    @Autowired
    private RepaymentServiceImpl repaymentServiceImpl;

    @GetMapping("/outstanding-balance/{applicationId}")
    public ResponseEntity<BigDecimal> getOutstandingBalance(@PathVariable Integer applicationId) {
        try {
            BigDecimal outstandingBalance = repaymentServiceImpl.getOutstandingBalance(applicationId);
            return ResponseEntity.ok(outstandingBalance);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/schedule/{applicationId}")
    public ResponseEntity<List<RepaymentResponseDTO>> getRepaymentSchedule(@PathVariable Integer applicationId) {
        try {
            List<RepaymentResponseDTO> schedule = repaymentServiceImpl.getRepaymentSchedule(applicationId);
            if (schedule.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(schedule);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/pay/{repaymentId}")
    public ResponseEntity<?> makePayment(@PathVariable Integer repaymentId, @RequestBody PaymentRequestDTO request) {
        try {
            repaymentServiceImpl.makePayment(repaymentId, request.getPaidAmount());
            return ResponseEntity.ok().body("Payment successfully recorded.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    // NEW ENDPOINT: Generates schedule from a request body
    @PostMapping("/generate-schedule")
    public ResponseEntity<?> generateRepaymentSchedule(@RequestBody RepaymentGenerationRequestDTO request) {
        try {
            repaymentServiceImpl.generateRepaymentSchedule(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Repayment schedule generated successfully.");
        } catch (IllegalArgumentException e) {
            // Catches "Loan application not found" error
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IllegalStateException e) {
            // Catches "Loan application not approved" error
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            // Catches any other unexpected errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
        }
    }
}