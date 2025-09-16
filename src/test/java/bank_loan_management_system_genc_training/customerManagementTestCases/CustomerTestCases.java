package bank_loan_management_system_genc_training.customerManagementTestCases;

public class CustomerTestCases {

    public boolean validateEmail(String email) {
        // Simple regex for email validation
        String emailRegex = "^[a-zA-Z0-9.]+@[a-zA-Z0-9.]+\\.[a-zA-Z]{2,}$";
        return email != null && email.matches(emailRegex);
    }

    public boolean validatePhone(String phone) {
        // Simple regex for phone number validation (10 digits)
        String phoneRegex = "^\\d{10}$";
        return phone != null && phone.matches(phoneRegex);
    }

    public boolean validateName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    public boolean validateAddress(String address) {
        return address != null && !address.trim().isEmpty();
    }

    public boolean validateKYCStatus(String kycStatus) {
        return kycStatus != null && (kycStatus.equals("PENDING") || kycStatus.equals("APPROVED") || kycStatus.equals("REJECTED"));
    }

    public boolean validateCustomer(String name, String address, String email, String phone, String kycStatus) {
        return validateName(name) && validateAddress(address) && validateEmail(email) && validatePhone(phone) && validateKYCStatus(kycStatus);
    }
}
