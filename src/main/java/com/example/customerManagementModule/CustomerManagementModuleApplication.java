package com.example.customerManagementModule;

import com.example.customerManagementModule.entity.Customer;
import com.example.customerManagementModule.entity.KYCStatus;
import com.example.customerManagementModule.entity.User;
import com.example.customerManagementModule.service.CustomerService;
import com.example.customerManagementModule.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//public class CustomerManagementModuleApplication implements CommandLineRunner {
public class CustomerManagementModuleApplication {

	@Autowired
	private UserService userService;

	@Autowired
	private CustomerService customerService;

	public static void main(String[] args) {
		SpringApplication.run(CustomerManagementModuleApplication.class, args);
	}

//	@Override
//	public void run(String... args) throws Exception {
//
//		Customer customer = new Customer();
//		customer.setName("Sanjeeva");
//		customer.setEmail("duos78550@gmail.com");
//		customer.setAddress("23/620, Chennai, Tamil Nadu");
//		customer.setPhone("8186863951");
//		customer.setKycStatus(KYCStatus.PENDING);
//
//		User user = new User();
//		user.setName("Sanjeeva");
//		user.setEmail("duos78550@gmail.com");
//		user.setPassword("Sanjeeva@123");
//
//		// Step 1: Save the customer and get the persisted instance back.
//		Customer savedCustomer = customerService.saveCustomer(customer);
//
//		// Step 2: Now, set the savedCustomer instance on the user object.
//		// This customer now has a valid database ID.
//		user.setCustomer(savedCustomer);
//
//		// Step 3: Save the user. Hibernate can now correctly link the user to the customer.
//		userService.saveUser(user);
//
//	}
}

