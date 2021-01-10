package by.nadia.controller;

import by.nadia.entity.Poll;
import by.nadia.entity.User;
import by.nadia.entity.UserAuth;
import by.nadia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping(path = "/registration")
    public ModelAndView userRegistration(ModelAndView modelAndView) {
        modelAndView.setViewName("registration");
        modelAndView.addObject("user", new User());
        return modelAndView;
    }


    @PostMapping(path = "/registration")
    public ModelAndView userRegistration (@Valid @ModelAttribute("user") User user, BindingResult bindingResult, ModelAndView modelAndView){
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("registration");
        } else {
            if(userService.save(user)==false){
                modelAndView.addObject("userNotReg", "This username is not available");
            }
 //           modelAndView.addObject("userReg", new User());
            modelAndView.setViewName("registration");

        }
        return modelAndView;
    }


    @GetMapping(path = "/authorization")
    public ModelAndView userAuthorization(ModelAndView modelAndView) {
        modelAndView.setViewName("authorization");
        modelAndView.addObject("user", new UserAuth());
        return modelAndView;
    }


    @PostMapping(path = "/authorization")
    public  ModelAndView userAuthorization (@Valid @ModelAttribute("user") UserAuth user, BindingResult bindingResult, ModelAndView modelAndView, HttpSession httpSession){
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("authorization");
        } else {
            User userByUsername = userService.getByUsername(user.getUsername());
            if (userByUsername!=null) {
                if (userByUsername.getPassword().equals(user.getPassword())) {
                    httpSession.setAttribute("user",userByUsername);
                }
            }
            modelAndView.setViewName("redirect:/");
        }
        return modelAndView;
    }

    @GetMapping(path = "/profile")
    public ModelAndView userProfile(ModelAndView modelAndView, HttpSession httpSession) {
        User user= (User) httpSession.getAttribute("user");
        List<User> users = userService.getAll();
        for (User user1: users) {
            if(user1.getUsername().equals(user.getUsername())) {
                modelAndView.addObject("userCurrent",user1);
            }
        }
        modelAndView.setViewName("profile");
        return modelAndView;
    }

    @PostMapping(path = "/profile")
    public ModelAndView userProfile(@Valid @ModelAttribute("userCurrent") User user, BindingResult bindingResult,ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("profile");
        } else {
            List<User> users = userService.getAll();
            for (User user1: users) {
                if(user1.getUsername().equals(user.getUsername())) {
                        userService.updateName(user.getName(), user1.getUsername());
                        userService.updateEmail(user.getEmail(), user1.getUsername());
                        userService.updatePassword(user.getPassword(), user1.getUsername());
                }
            }
            modelAndView.setViewName("profile");

        }

        return modelAndView;
    }

    @GetMapping(path = "/logout")
    public ModelAndView logout(ModelAndView modelAndView, HttpSession httpSession){
        httpSession.invalidate();
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

}
