package com.example.customerManagementModule.repository;

import com.example.customerManagementModule.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

    User findByEmail(String email);

}
