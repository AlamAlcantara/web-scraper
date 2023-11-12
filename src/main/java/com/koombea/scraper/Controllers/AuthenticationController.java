package com.koombea.scraper.Controllers;


import com.koombea.scraper.dto.UserDto;
import com.koombea.scraper.exception.UsernameAlreadyExistsException;
import com.koombea.scraper.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthenticationController {

    @Autowired
    private UserService userService;
    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@ModelAttribute("user") UserDto userDto,
                               BindingResult result,
                               Model model){

        String url = "register";

        try{
            userService.save(userDto);
            url = "login";
        } catch (UsernameAlreadyExistsException ex) {
            result.rejectValue("username", null, ex.getMessage());
            model.addAttribute("user", userDto);
        }

        return url;
    }

}
