package bank_loan_management_system_genc_training.customerModule.service;

import bank_loan_management_system_genc_training.customerModule.entity.Customer;
import bank_loan_management_system_genc_training.customerModule.repository.CustomerRepository;
import bank_loan_management_system_genc_training.customerModule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerServiceImpl implements CustomerService{

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

