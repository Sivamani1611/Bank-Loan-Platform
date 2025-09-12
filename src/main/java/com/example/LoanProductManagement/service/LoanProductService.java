package com.example.LoanProductManagement.service;

import com.example.LoanProductManagement.dto.LoanProductDTO;
import com.example.LoanProductManagement.entity.LoanProduct;
import com.example.LoanProductManagement.repository.LoanProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoanProductService {

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
        originalLoanProduct.setIntrestRate(updatedLoanProduct.getIntrestRate());
        originalLoanProduct.setTenure(updatedLoanProduct.getTenure());
        originalLoanProduct.setMaxAmount(updatedLoanProduct.getMaxAmount());
        originalLoanProduct.setMinAmount(updatedLoanProduct.getMinAmount());
        return loanProductRepository.save(originalLoanProduct);
    }

}