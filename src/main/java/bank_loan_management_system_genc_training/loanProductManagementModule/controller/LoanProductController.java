package bank_loan_management_system_genc_training.loanProductManagementModule.controller;


import bank_loan_management_system_genc_training.loanProductManagementModule.dto.LoanProductDTO;
import bank_loan_management_system_genc_training.loanProductManagementModule.entity.LoanProduct;
import bank_loan_management_system_genc_training.loanProductManagementModule.service.LoanProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loanProduct")
public class LoanProductController {

    @Autowired
    private LoanProductService loanProductService;


/*
    {
            "productName": "Personal Loan",
            "intrestRate": 8.5,
            "minAmount": 50000.0,
            "maxAmount": 500000.0,
            "tenure": 60
    }
*/

    @PostMapping("/save")
    public ResponseEntity<LoanProduct> save(@RequestBody LoanProduct loanProduct){
        System.out.println("Received Data to Save: "+ loanProduct.getProductName());
        return ResponseEntity.ok().body(loanProductService.save(loanProduct));
    }

    @GetMapping("/retrieve/all")
    public ResponseEntity<LoanProductDTO> fetechAll(){
        System.out.println("Request to Retrieve all the Loan Products Data");
        return ResponseEntity.ok().body(loanProductService.findAll());
    }

    @DeleteMapping("/delete/{loanProductId}")
    public void deleteLoanProduct(@PathVariable Integer loanProductId){
        System.out.println("Received Request to Delete LoanProduct: "+loanProductId);
        loanProductService.deleteByLoanProductId(loanProductId);
    }

    @GetMapping("/retrieve/{loanProductId}")
    public ResponseEntity<LoanProduct> fetechByProductId(@PathVariable Integer loanProductId){
        System.out.println("Request to retrieve data of: "+loanProductId);
        return ResponseEntity.ok().body(loanProductService.findByLoanProductId(loanProductId));
    }

    @PostMapping("/update/{loanProductId}")
    public ResponseEntity<LoanProduct> updateLoanProduct(@PathVariable Integer loanProductId, @RequestBody LoanProduct loanProduct){
        System.out.println("request to update the LoanProductId: "+loanProductId);
        System.out.println("Request to change the LoanProduct details into: "+loanProduct.getProductName());
        LoanProduct updatedProduct = loanProductService.updateLoanProduct(loanProductId,loanProduct);
        return new ResponseEntity<>(updatedProduct,HttpStatus.OK);
    }

/*
    {
            "productName": "Personal Loan - Updated Name",
            "intrestRate": 8.5,
            "minAmount": 50000.0,
            "maxAmount": 500000.0,
            "tenure": 60
    }
*/
}
