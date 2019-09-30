package cn.ibm.com.controller;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import cn.ibm.com.core.base.BaseController;
import cn.ibm.com.core.constant.ErrorCode;
import cn.ibm.com.core.exception.BusinessException;
import cn.ibm.com.core.rest.RestResult;
import cn.ibm.com.core.rest.RestResultGenerator;
import cn.ibm.com.entity.LabUser;
import cn.ibm.com.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class LoginController extends BaseController{
	
	@Autowired
    private UserService userService;

    @GetMapping(value = "/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout, HttpSession session, Model model) {

        AuthenticationException ex = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        String errorMessage = (error != null && ex != null) ? ex.getMessage() : null;

        if (logout != null) {
            errorMessage = "You have been successfully logged out !!";
        }

        model.addAttribute("errorMessage", errorMessage);

        return "login";
    }
    

    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }

    
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    
    @PostMapping("/register")
    @ResponseBody
    public RestResult<LabUser> doRegister(@RequestParam(name = "kaptcha", required = true) String kaptcha,
            @Valid @RequestBody LabUser user, HttpSession session) {

        checkCaptcha(kaptcha, session);

        LabUser existingUser = userService.findByUsername(user.getName());
        if (existingUser != null) {
            throw BusinessException.getInstance(ErrorCode.USER_EXISTS);
        }

        userService.saveUser(user);
        
        return RestResultGenerator.genSuccessResult(user);
    }

}
