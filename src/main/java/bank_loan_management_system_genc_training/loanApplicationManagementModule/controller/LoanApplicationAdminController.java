package bank_loan_management_system_genc_training.loanApplicationManagementModule.controller;

import bank_loan_management_system_genc_training.loanApplicationManagementModule.dto.LoanApplicationDashboardData;
import bank_loan_management_system_genc_training.loanApplicationManagementModule.entity.LoanApplication;
import bank_loan_management_system_genc_training.loanApplicationManagementModule.entity.LoanApprovalStatus;
import bank_loan_management_system_genc_training.loanApplicationManagementModule.service.LoanAppAdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/loanApplications")
public class LoanApplicationAdminController {

    @Autowired
    private LoanAppAdminServiceImpl loanAppAdminServiceImpl;

    @GetMapping
    public ResponseEntity<LoanApplicationDashboardData> getAllApplications() {
        LoanApplicationDashboardData dashboardData = loanAppAdminServiceImpl.getAdminDashboardData();
        return ResponseEntity.ok(dashboardData);
    }

    @PutMapping("/update-status/{applicationId}")
    public ResponseEntity<LoanApplication> updateStatus(
            @PathVariable Integer applicationId,
            @RequestBody String newStatus) {

        LoanApprovalStatus status;
        try {
            status = LoanApprovalStatus.valueOf(newStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        LoanApplication updatedLoan = loanAppAdminServiceImpl.updateLoanStatus(applicationId, status);

        if (updatedLoan != null) {
            return ResponseEntity.ok(updatedLoan);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}