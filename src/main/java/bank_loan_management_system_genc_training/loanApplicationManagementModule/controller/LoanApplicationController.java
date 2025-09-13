package bank_loan_management_system_genc_training.loanApplicationManagementModule.controller;

import bank_loan_management_system_genc_training.loanApplicationManagementModule.dto.IterableDTO;
import bank_loan_management_system_genc_training.loanApplicationManagementModule.dto.LoanApplicationDTO;
import bank_loan_management_system_genc_training.loanApplicationManagementModule.entity.LoanApplication;
import bank_loan_management_system_genc_training.loanApplicationManagementModule.entity.LoanApprovalStatus;
import bank_loan_management_system_genc_training.loanApplicationManagementModule.service.LoanApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("/api/loanApplication")
public class LoanApplicationController {

    @Autowired
    private LoanApplicationService loanApplicationService;


    @PostMapping("/save")
    public ResponseEntity<LoanApplication> saveLoanApp(@RequestBody LoanApplication loanApp) {
        return ResponseEntity.ok().body(loanApplicationService.save(loanApp));
    }

    /*
    {
        "customerId": 1,
        "loanProductId": 2,
        "loanAmount": 1234.23,
        "applicationDate": "2025-09-10",
        "approvalStatus": "PENDING"
    }
     */

    @GetMapping("/retreive/all")
    public ResponseEntity<IterableDTO> fetchAll() {
        return ResponseEntity.ok().body(loanApplicationService.findAll());
    }

    @GetMapping("/retreive/{loanAppId}")
    public ResponseEntity<LoanApplication> fetchLoanAppById(@PathVariable Integer loanAppId) {
        return ResponseEntity.ok().body(loanApplicationService.findById(loanAppId));
    }

    @GetMapping("/retreive/customer/{customerId}")
    public ResponseEntity<LoanApplicationDTO> fetchByCustomerId(@PathVariable Integer customerId) {
        return ResponseEntity.ok().body(loanApplicationService.findByCustomerId(customerId));
    }

    @GetMapping("/retreive/loanProduct/{loanProductId}")
    public ResponseEntity<LoanApplicationDTO> fetchByLoanProductId(@PathVariable Integer loanProductId) {
        return ResponseEntity.ok().body(loanApplicationService.findByLoanProductId(loanProductId));
    }

    @GetMapping("/retreive/applicationDate/{loanAppDate}")
    public ResponseEntity<LoanApplicationDTO> fetchByLoanAppDate(@PathVariable Date loanAppDate) {
        return ResponseEntity.ok().body(loanApplicationService.findByApplicationDate(loanAppDate));
    }

    @GetMapping("/retreive/approvalStatus/{loanApproval}")
    public ResponseEntity<LoanApplicationDTO> fetchByApprovalStatus(@PathVariable LoanApprovalStatus loanApproval) {
        return ResponseEntity.ok().body(loanApplicationService.findByLoanApprovalStatus(loanApproval));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<LoanApplication> deleteById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(loanApplicationService.deleteById(id));
    }

}
