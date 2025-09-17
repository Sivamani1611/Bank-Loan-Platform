package bank_loan_management_system_genc_training.loanProductManagementModule.service;

import bank_loan_management_system_genc_training.loanProductManagementModule.dto.LoanProductDTO;
import bank_loan_management_system_genc_training.loanProductManagementModule.entity.LoanProduct;
import bank_loan_management_system_genc_training.loanProductManagementModule.repository.LoanProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoanProductServiceImpl implements LoanProductService {

    @Autowired
    private LoanProductRepository loanProductRepository;

    public LoanProduct save(LoanProduct loanProduct){
        return loanProductRepository.save(loanProduct);
    }

    public void deleteByLoanProductId(Integer loanProductId){
        loanProductRepository.deleteById(loanProductId);
    }

    public LoanProductDTO findAll(){
        LoanProductDTO loanProductDTO = new LoanProductDTO();
        loanProductDTO.setLoanProduct(loanProductRepository.findAll());
        return loanProductDTO;
    }

    public LoanProduct findByLoanProductId(Integer loanProductId){
        Optional<LoanProduct> loanProductOpt = loanProductRepository.findByLoanProductId(loanProductId);
        return loanProductOpt.orElse(null);
    }

    public LoanProduct updateLoanProduct(Integer loanProductId, LoanProduct updatedLoanProduct){
        Optional<LoanProduct> presentLoanProduct = loanProductRepository.findByLoanProductId(loanProductId);
        LoanProduct originalLoanProduct = presentLoanProduct.get();
        originalLoanProduct.setProductName(updatedLoanProduct.getProductName());
        originalLoanProduct.setInterestRate(updatedLoanProduct.getInterestRate());
        originalLoanProduct.setTenure(updatedLoanProduct.getTenure());
        originalLoanProduct.setMaxAmount(updatedLoanProduct.getMaxAmount());
        originalLoanProduct.setMinAmount(updatedLoanProduct.getMinAmount());
        return loanProductRepository.save(originalLoanProduct);
    }

}