package com.example.LoanProductManagement;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HtmlFileController {

    @GetMapping
    public ModelAndView showLoanAppPage() {
        return new ModelAndView("loanProduct");
    }
}
