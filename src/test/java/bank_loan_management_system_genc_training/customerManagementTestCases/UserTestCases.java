package bank_loan_management_system_genc_training.customerManagementTestCases;

public class UserTestCases {

    public boolean validateUsername(String username) {
        return username != null && !username.trim().isEmpty() && username.length() >= 3;
    }

    public boolean validatePassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$";
        return password != null && password.matches(regex);
    }
}
