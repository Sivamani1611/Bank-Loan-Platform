package bank_loan_management_system_genc_training.customerModule.service;

import bank_loan_management_system_genc_training.customerModule.entity.User;

public interface UserService {

    User saveUser(User user);

    User findUserByEmail(String email);

}
