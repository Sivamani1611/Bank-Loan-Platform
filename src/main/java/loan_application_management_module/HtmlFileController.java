package loan_application_management_module;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HtmlFileController {

    @GetMapping("/admin")
    public ModelAndView showAdminLoanAppPage() {
        return new ModelAndView("AdminLoanAppModule");
    }

    @GetMapping("/customer")
    public ModelAndView showCustomerLoanAppPage() {
        return new ModelAndView("CustomerLoanApplicationModule");
    }
}
