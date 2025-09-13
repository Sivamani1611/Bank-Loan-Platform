package bank_loan_management_system_genc_training.customerModule.controller;

import bank_loan_management_system_genc_training.customerModule.entity.User;
import bank_loan_management_system_genc_training.customerModule.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return ResponseEntity.ok().body(userService.saveUser(user));
    }

    @GetMapping("/retrieve/{email}")
    public ResponseEntity<User> getUser(@PathVariable String email) {
        return ResponseEntity.ok().body(userService.findUserByEmail(email));
    }


}
