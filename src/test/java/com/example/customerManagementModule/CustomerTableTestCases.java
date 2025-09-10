package com.example.customerManagementModule;

import com.example.customerManagementModule.entity.Customer;
import com.example.customerManagementModule.entity.KYCStatus;
import com.example.customerManagementModule.repository.CustomerRepository;
import com.example.customerManagementModule.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerTableTestCases {

	@Mock
	private CustomerRepository customerRepo;

	@InjectMocks
	private CustomerService customerService;

	@Test
	void test_save_customer_with_all_fields() {

		Customer customer = new Customer();
		customer.setName("Sanjeeva");
		customer.setEmail("duos78550@gmail.com");
		customer.setKycStatus(KYCStatus.PENDING);
		customer.setAddress("Some Address");
		customer.setPhone("123456789");

		// Mock the behavior of the customerRepo.save() method
		// You're telling Mockito that when customerRepo.save() is called with any Customer object,
		// it should return the customer object you just created.
		Mockito.when(customerRepo.save(Mockito.any(Customer.class))).thenReturn(customer);

		Customer savedCustomer = customerService.saveCustomer(customer);

		assertTrue(customer.equals(savedCustomer));

		// Assert that savedCustomer is not null, which it would be without the mock
		assertNotNull(savedCustomer);

		// Your original assertion will now pass
		assertTrue(customer.getEmail().equals(savedCustomer.getEmail()));
	}
}