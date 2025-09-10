package com.example.customerManagementModule.controller;


import com.example.customerManagementModule.entity.Customer;
import com.example.customerManagementModule.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/save")
    public ResponseEntity<Customer> saveCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok().body(customerService.saveCustomer(customer));
    }


    @GetMapping("/retrieve/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
        return ResponseEntity.ok().body(customerService.findCustomerByEmail(email));
    }

    @DeleteMapping("/delete/{email}")
    public ResponseEntity<String> deleteCustomerByEmail(@PathVariable String email) {
        return ResponseEntity.ok().body(customerService.deleteCustomerByEmail(email));
    }



}
