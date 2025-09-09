package com.example.customerManagementModule.repository;

import com.example.customerManagementModule.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    Customer findByEmail(String email);

}
