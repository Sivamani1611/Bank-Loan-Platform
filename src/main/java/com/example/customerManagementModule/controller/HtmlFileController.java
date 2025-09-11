package com.example.customerManagementModule.controller;

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

    @GetMapping("/admin/dashboard")
    public String showAdminDashboardPage() {
        return "Admin Dashboard";
    }

    @GetMapping("/customer/dashboard")
    public String showCustomerDashboardPage() {
        return "Customer Dashboard";
    }
}
