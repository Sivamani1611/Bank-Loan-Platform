package bank_loan_management_system_genc_training.customerModule.repository;

import bank_loan_management_system_genc_training.customerModule.entity.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    Customer findByEmail(String email);
}
