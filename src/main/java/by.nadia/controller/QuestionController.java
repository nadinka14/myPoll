package by.nadia.controller;

import by.nadia.entity.Question;
import by.nadia.entity.User;
import by.nadia.entity.UserAuth;
import by.nadia.service.PollService;
import by.nadia.service.QuestionService;
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

@RequestMapping(path = "/question")
@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    private PollService pollService;

    @GetMapping
    public ModelAndView userAuthorization(ModelAndView modelAndView) {
        modelAndView.setViewName("question");
        return modelAndView;
    }

    @PostMapping(path = "/create")
    public  ModelAndView userAuthorization (@Valid @ModelAttribute("question") Question question, BindingResult bindingResult, ModelAndView modelAndView, HttpSession httpSession){
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("question");
        } else {
            questionService.save(question);
            modelAndView.setViewName("question");
        }
        return modelAndView;
    }
}
