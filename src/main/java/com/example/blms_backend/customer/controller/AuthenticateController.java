package com.example.blms_backend.customer.controller;

import com.example.blms_backend.customer.model.Customer;
import com.example.blms_backend.customer.model.Password;
import com.example.blms_backend.customer.service.CustomerService;
import com.example.blms_backend.customer.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthenticateController {

    @Autowired
    private CustomerService customerService;


    @Autowired
    private PasswordService passwordService;

    @GetMapping("/{username}/{pass}")
    public ResponseEntity<Customer> authorize(@PathVariable String username, @PathVariable String pass) {

        Customer customer = customerService.findCustomerByName(username);
        Password password = passwordService.findByPassword(pass);

        if(customer != null && password != null) {
            return ResponseEntity.ok(customer);
        }

        return ResponseEntity.badRequest().body(null);
    }

}
