package bank_loan_management_system_genc_training.reportsModule.service;

import bank_loan_management_system_genc_training.reportsModule.dto.ReportDTO;
import bank_loan_management_system_genc_training.reportsModule.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    public ReportDTO getDashboardData() {
        return reportRepository.getDashboardData();
    }
}
