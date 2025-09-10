package loan_application_management_module.service;

import loan_application_management_module.dto.IterableDTO;
import loan_application_management_module.dto.LoanApplicationDTO;
import loan_application_management_module.entity.LoanApplication;
import loan_application_management_module.entity.LoanApprovalStatus;
import loan_application_management_module.repository.LoanApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class LoanApplicationService {

    @Autowired
    private LoanApplicationRepository loanApplicationRepo;

    public LoanApplication save(LoanApplication loanApp) {
        return loanApplicationRepo.save(loanApp);
    }

    public LoanApplication findById(Integer id) {
        Optional<LoanApplication> loanAppOpt = loanApplicationRepo.findById(id);

        if(loanAppOpt.isPresent()) {
            return loanAppOpt.get();
        }
        return null;
    }

    public IterableDTO findAll() {
        IterableDTO iterableDTO = new IterableDTO();
        iterableDTO.setLoanAppIterable(loanApplicationRepo.findAll());
        return iterableDTO;
    }

    public LoanApplicationDTO findByCustomerId(Integer customerId) {
        LoanApplicationDTO loanAppDto = new LoanApplicationDTO();
        loanAppDto.setListOfLoanApplications(loanApplicationRepo.findByCustomerId(customerId));
        return loanAppDto;
    }

    public LoanApplicationDTO findByLoanProductId(Integer loanProductId) {
        LoanApplicationDTO loanAppDto = new LoanApplicationDTO();
        loanAppDto.setListOfLoanApplications(loanApplicationRepo.findByLoanProductId(loanProductId));
        return loanAppDto;
    }

    public LoanApplicationDTO findByApplicationDate(Date loanAppDate) {
        LoanApplicationDTO loanAppDto = new LoanApplicationDTO();
        loanAppDto.setListOfLoanApplications(loanApplicationRepo.findByApplicationDate(loanAppDate));
        return loanAppDto;
    }

    public LoanApplicationDTO findByLoanApprovalStatus(LoanApprovalStatus loanApprovalStatus) {
        LoanApplicationDTO loanAppDto = new LoanApplicationDTO();
        loanAppDto.setListOfLoanApplications(loanApplicationRepo.findByApprovalStatus(loanApprovalStatus));
        return loanAppDto;
    }

    public LoanApplication deleteById(Integer id) {
        Optional<LoanApplication> loanAppOpt = loanApplicationRepo.findById(id);

        if(loanAppOpt.isPresent()) {
            loanApplicationRepo.deleteById(id);
            return loanAppOpt.get();
        }

        return null;
    }

}
