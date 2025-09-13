package bank_loan_management_system_genc_training.customerModule.repository;

import bank_loan_management_system_genc_training.customerModule.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByEmail(String email);

}
