package bank_loan_management_system_genc_training.customerModule.service;

import bank_loan_management_system_genc_training.customerModule.entity.User;
import bank_loan_management_system_genc_training.customerModule.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public User saveUser(User user) {
        return userRepo.save(user);
    }

    public User findUserByEmail(String email) {
        return userRepo.findByEmail(email);
    }

}


