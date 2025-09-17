package bank_loan_management_system_genc_training.reportsModule.controller;

import bank_loan_management_system_genc_training.reportsModule.dto.ReportDTO;
import bank_loan_management_system_genc_training.reportsModule.service.ReportServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportServiceImpl reportServiceImpl;

    public ReportController(ReportServiceImpl reportServiceImpl) {
        this.reportServiceImpl = reportServiceImpl;
    }

    @GetMapping("/dashboard-data")
    public ResponseEntity<ReportDTO> getDashboardData() {
        try {
            ReportDTO dashboardData = reportServiceImpl.getDashboardData();
            return ResponseEntity.ok(dashboardData);
        } catch (RuntimeException e) {
            // Log the exception for debugging purposes
            e.printStackTrace();
            // Return an HTTP 500 error with an empty body or a specific error message
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}