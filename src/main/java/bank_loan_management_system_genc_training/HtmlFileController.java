package bank_loan_management_system_genc_training;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HtmlFileController {

    @GetMapping("/")
    public ModelAndView showLandingPage() {
        return new ModelAndView("index");
    }

    @GetMapping("/login")
    public ModelAndView showLoginPage() {
        return new ModelAndView("login");
    }

    @GetMapping("/register")
    public ModelAndView showRegisterPage() {
        return new ModelAndView("register");
    }

    @GetMapping("/resetPassword")
    public ModelAndView showForgotPasswordPage() {return new ModelAndView("resetPassword");}

    @GetMapping("/admin/loanApplication")
    public ModelAndView showAdminLoanAppPage() {
        return new ModelAndView("AdminLoanAppModule");
    }

    @GetMapping("/customer/loanApplication")
    public ModelAndView showCustomerLoanAppPage() {
        return new ModelAndView("CustomerLoanApplicationModule");
    }

    @GetMapping("/customer/loanProduct")
    public ModelAndView showCustomerLoanProductPage() {
        return new ModelAndView("CustomerLoanProductModule");
    }

}
