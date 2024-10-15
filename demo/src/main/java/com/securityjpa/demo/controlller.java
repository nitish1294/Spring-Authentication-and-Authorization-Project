package com.securityjpa.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class controlller {
	@GetMapping({"/","/home"})
	public String index() {
		return "index";
	}
	@GetMapping("/admin")
	public String admin() {
		return"admin";
		
	}
	@GetMapping("/user")
	public String user() {
		return "user";
	}
	@GetMapping("/login")
    public String showLoginPage() {
        return "Login";  // Refers to login.html in templates folder
    }

}
