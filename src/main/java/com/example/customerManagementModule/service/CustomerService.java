package com.example.customerManagementModule.service;

import com.example.customerManagementModule.entity.Customer;
import com.example.customerManagementModule.entity.User;
import com.example.customerManagementModule.repository.CustomerRepository;
import com.example.customerManagementModule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepo;

    @Autowired
    private UserRepository userRepo;

    public Customer saveCustomer(Customer customer) {
        return customerRepo.save(customer);
    }

    public Customer findCustomerByEmail(String email) {
        return customerRepo.findByEmail(email);
    }

    public String deleteCustomerByEmail(String email) {
        Customer customer = customerRepo.findByEmail(email);

        if(customer != null) {
            customerRepo.deleteById(customer.getCustomerId());
            return "Customer with " + customer.getEmail() + " email has deleted successfully";
        }

        return "Customer with " + email + " not found!";
    }

}

