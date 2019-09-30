package cn.ibm.com.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.ibm.com.core.base.BaseController;
import cn.ibm.com.core.rest.RestResult;
import cn.ibm.com.core.rest.RestResultGenerator;
import cn.ibm.com.entity.LabUser;
import cn.ibm.com.service.UserService;


@Controller
@RequestMapping("/user")
public class UserController extends BaseController{

	@Autowired
    private UserService userService;

    @GetMapping("/account")
    public String preUpdateAccount(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        LabUser user = userService.findByUsername(username);

        model.addAttribute("user", user);

        return "user/account_edit";
    }
    

    @PutMapping("/account")
    @ResponseBody
    public RestResult<LabUser> updateAccount(@RequestParam(name = "kaptcha", required = true) String kaptcha,
            @Valid @RequestBody LabUser user, HttpSession session) {

        checkCaptcha(kaptcha, session);

        userService.updateUser(user);

        return RestResultGenerator.genSuccessResult(user);
    }

    
    @GetMapping("/tutorials")
    public String listTutorials(Model model) {
        model.addAttribute("activeLink", "tutorials");
        return "user/tutorials";
    }
}
