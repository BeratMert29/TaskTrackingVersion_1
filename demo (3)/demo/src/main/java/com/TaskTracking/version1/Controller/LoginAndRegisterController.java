package com.TaskTracking.version1.Controller;

import com.TaskTracking.version1.Service.LoginService;
import com.TaskTracking.version1.Service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginAndRegisterController {

    private final LoginService loginService;
    private final RegisterService registerService;

    @Autowired
    public LoginAndRegisterController(LoginService loginService, RegisterService registerService) {
        this.loginService = loginService;
        this.registerService = registerService;
    }

    @PostMapping("/login")
    public String Login(@RequestParam String username, @RequestParam String password) {
       Boolean response = loginService.login(username, password);
       if (response) {
           return "Login successful";
       }
        return "Wrong username or password";
    }

    @PostMapping("/register")
    public String Register(@RequestParam String username, @RequestParam String password, @RequestParam String role) {
        if (registerService.register(username, password, role)){
            return "Register successful";
        }
        return "Register failed";
    }
}
