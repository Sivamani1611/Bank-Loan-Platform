package bank_loan_management_system_genc_training;

import bank_loan_management_system_genc_training.customerManagementTestCases.CustomerTestCases;
import bank_loan_management_system_genc_training.customerManagementTestCases.UserTestCases;
import bank_loan_management_system_genc_training.customerModule.entity.Customer;
import bank_loan_management_system_genc_training.customerModule.entity.KYCStatus;
import bank_loan_management_system_genc_training.customerModule.entity.User;
import bank_loan_management_system_genc_training.customerModule.service.CustomerService;
import org.junit.jupiter.api.Assertions; // Corrected import for JUnit 5
import org.junit.jupiter.api.BeforeEach; // Corrected import for JUnit 5
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith; // Corrected import for JUnit 5
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension; // Corrected import for JUnit 5

@ExtendWith(MockitoExtension.class) // Replaced @RunWith(MockitoJUnitRunner.class)
class BankLoanManagementSystemGencTrainingApplicationTests {

    @Mock
    CustomerService customerService;

    @Mock
    CustomerTestCases customerTestCases;

    @Mock
    UserTestCases userTestCases;

    // Use InjectMocks if CustomerService had dependencies you needed to inject
    // @InjectMocks
    // CustomerService customerService;

    // We don't need a mock customer, we'll create a new one for each test
    // @Mock
    // Customer customer;

    private Customer customerWithValidDetails;
    private Customer customerWithInValidDetails;

    private User userWithValidDetails;
    private User userWithInValidDetails;

    @BeforeEach // Replaced @Before for JUnit 5
    public void setUp() {
        customerWithValidDetails = new Customer(); // Create a new instance
        customerWithInValidDetails = new Customer(); // Create a new instance

        customerWithValidDetails.setName("John Doe");
        customerWithValidDetails.setAddress("123 Main St");
        customerWithValidDetails.setEmail("johndoe@gmail.com");
        customerWithValidDetails.setKycStatus(KYCStatus.PENDING);
        customerWithValidDetails.setPhone("123-456-7890");

        customerWithInValidDetails.setName("");
        customerWithInValidDetails.setAddress("   ");
        customerWithInValidDetails.setEmail("invalid-email");
        customerWithInValidDetails.setKycStatus(null);
        customerWithInValidDetails.setPhone("12345");

        System.out.println("Setup complete, new customer instance created.");

        userWithValidDetails = new User();

        userWithValidDetails.setEmail("johndoe@gmail.com");
        userWithValidDetails.setName("John Doe");
        userWithValidDetails.setPassword("VSkv");
        userWithValidDetails.setRole("customer");
        System.out.println("Setup complete, new user instance created.");

    }

    @Test
    void testCreateCustomer_withValidData_shouldSucceedAndReturnCreatedCustomer() {
        // Arrange
        // This is the core of Mockito: define what the mock should do
        // when its method is called. Here, we tell `saveCustomer` to return
        // the same customer object that was passed to it.
        Mockito.when(customerService.saveCustomer(Mockito.any(Customer.class)))
                .thenReturn(customerWithValidDetails);

        // Act
        // Now, we call the method on the mock object.
        Customer createdCustomer = customerService.saveCustomer(customerWithValidDetails);

        // Assert
        // We assert that the returned object is not null and has the expected email.
        Assertions.assertNotNull(createdCustomer);
        Assertions.assertEquals("johndoe@gmail.com", createdCustomer.getEmail());
        Assertions.assertEquals(customerWithValidDetails, createdCustomer); // Further assert that it's the same object

        // Optional: Verify that the `saveCustomer` method was called exactly once
        // with an object of type Customer.
        // Mockito.verify(customerService, Mockito.times(1)).saveCustomer(Mockito.any(Customer.class));
    }

    @Test
    void testCreateCustomer_withInvalidData_shouldThrowException() {
        System.out.println(Mockito.when(customerService.saveCustomer(Mockito.any(Customer.class)))
                .thenReturn(customerWithInValidDetails));
        // Act
        // Now, we call the method on the mock object.
        Customer createdCustomer = customerService.saveCustomer(customerWithInValidDetails);

        // Assert
        // We assert that the returned object is not null and has the expected email.
        Assertions.assertNotNull(createdCustomer);
        Assertions.assertEquals(customerWithInValidDetails, createdCustomer); // Further assert that it's the same object
        Assertions.assertFalse(customerTestCases.validateEmail(createdCustomer.getEmail()));
        Assertions.assertFalse(customerTestCases.validatePhone(createdCustomer.getPhone()));
        Assertions.assertFalse(customerTestCases.validateName(createdCustomer.getName()));

        Assertions.assertNotNull(createdCustomer.getKycStatus());
    }

    @Test
    void testValidateUser_withValidData_shouldReturnUser() {

        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$";

        Mockito.when(userTestCases.validateUsername(userWithValidDetails.getName()))
                .thenReturn(true);
        Mockito.when(userTestCases.validatePassword(userWithValidDetails.getPassword()))
                .thenReturn(true);

        Assertions.assertTrue(userTestCases.validateUsername(userWithValidDetails.getName()));
        Assertions.assertTrue(userTestCases.validatePassword(userWithValidDetails.getPassword()));

        Assertions.assertTrue(userWithValidDetails.getPassword().matches(regex));

    }
}