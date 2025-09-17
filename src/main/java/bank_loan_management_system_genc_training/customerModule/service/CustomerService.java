package bank_loan_management_system_genc_training.customerModule.service;

import bank_loan_management_system_genc_training.customerModule.entity.Customer;

public interface CustomerService {

    Customer saveCustomer(Customer customer);

    Customer findCustomerByEmail(String email);

    String deleteCustomerByEmail(String email);


}
