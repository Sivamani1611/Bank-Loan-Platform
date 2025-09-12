package com.example.LoanProductManagement.repository;

import com.example.LoanProductManagement.entity.LoanProduct;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LoanProductRepository extends CrudRepository<LoanProduct, Integer> {

     Optional<LoanProduct> findByLoanProductId(Integer loanProductId);
}
