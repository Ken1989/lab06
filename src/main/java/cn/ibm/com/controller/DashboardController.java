package cn.ibm.com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cn.ibm.com.entity.LabUser;
import cn.ibm.com.service.UserService;

@Controller
public class DashboardController {

	@Autowired
    private UserService userService;

	
    @GetMapping("/")
    public String home() {
        return "dashboard";
    }
    

    @GetMapping("/admin/users")
    public String users(Model model) {
        List<LabUser> users = userService.findAllUsers();
        model.addAttribute("users", users);
        model.addAttribute("activeLink", "admin");
        return "admin/users";
    }

    
    @GetMapping(value = "/admin/admin")
    public String adminPage(Model model) {
        model.addAttribute("activeLink", "admin");
        return "admin/admin";

    }
}
