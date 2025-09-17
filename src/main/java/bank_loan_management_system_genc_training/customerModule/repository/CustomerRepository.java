package bank_loan_management_system_genc_training.customerModule.repository;

import bank_loan_management_system_genc_training.customerModule.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByEmail(String email);
}
