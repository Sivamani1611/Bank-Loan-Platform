package bank_loan_management_system_genc_training.customerModule.repository;

import bank_loan_management_system_genc_training.customerModule.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

}
