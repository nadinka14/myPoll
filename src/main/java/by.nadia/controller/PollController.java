package by.nadia.controller;


import by.nadia.entity.Poll;
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
import java.util.List;

@RequestMapping(path = "/poll")
@Controller
public class PollController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private PollService pollService;


    @PostMapping(path = "/showQuestion")
    public ModelAndView showPoll(ModelAndView modelAndView, HttpSession httpSession) {
        Poll poll= (Poll) httpSession.getAttribute("pollCurrent");
        System.out.println("-1"+poll.toString());
        if(poll!=null){
            List<Poll> polls = pollService.getAll();
            for (Poll poll1 : polls) {
                if(poll1.getName().equals(poll.getName())){
                    List<Question> allQuestions=questionService.getByIdPoll(poll1.getId());
                    httpSession.setAttribute("questionOfPoll", allQuestions);
                    System.out.println("+1");
                }

            }
        }

        modelAndView.setViewName("poll");
        return modelAndView;
    }


    @GetMapping
    public ModelAndView getPoll(ModelAndView modelAndView, HttpSession httpSession) {
        User user= (User) httpSession.getAttribute("user");
        Poll poll= (Poll) httpSession.getAttribute("pollCurrent");
        if(poll!=null){
            System.out.println("0"+poll.getName());
            List<Poll> polls = pollService.getAll();
            for (Poll poll1 : polls) {
                if(poll1.getName().equals(poll.getName())){
                    modelAndView.addObject("poll", poll);
                    List<Question> allQuestions=questionService.getByIdPoll(poll1.getId());
                    httpSession.setAttribute("questionOfPoll", allQuestions);
                    System.out.println("1");
                }

            }
        }
        else {
            System.out.println("2");
            Poll newPoll = new Poll();
            newPoll.setAuthorUsername(user.getUsername());
            modelAndView.addObject("poll", newPoll);
        }
        modelAndView.setViewName("poll");
        return modelAndView;
    }

    @PostMapping(path = "/showPoll")
    public ModelAndView getPolls(ModelAndView modelAndView,HttpSession httpSession){
        User user= (User) httpSession.getAttribute("user");
        Poll poll= (Poll) httpSession.getAttribute("pollCurrent");
        if(poll!=null){
            List<Poll> polls = pollService.getAll();
            for (Poll poll1 : polls) {
                if(poll1.getName().equals(poll.getName())){
                    modelAndView.addObject("poll", poll);
                }

            }
        }
        else {
            Poll newPoll = new Poll();
            newPoll.setAuthorUsername(user.getUsername());
            modelAndView.addObject("poll", newPoll);
        }
        modelAndView.setViewName("poll");
        return modelAndView;
    }

    @PostMapping(path = "/createQuestion")
    public ModelAndView createQuestion(ModelAndView modelAndView, HttpSession httpSession) {
         Poll poll= (Poll) httpSession.getAttribute("pollCurrent");
           List<Poll> polls = pollService.getAll();
                for (Poll poll1 : polls) {
                    if (poll1.getName().equals(poll.getName())) {
                        Question question=new Question();
                        question.setIdPoll(poll1.getId());
                        modelAndView.addObject("question", question);
                    }
                    }

            modelAndView.setViewName("question");
        return modelAndView;
    }

    @PostMapping(path = "/savePoll")
    public ModelAndView savePoll(@Valid @ModelAttribute("poll") Poll poll, BindingResult bindingResult, ModelAndView modelAndView,HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("poll");
        } else {
     /*       List<Poll> polls = pollService.getAll();
                for (Poll poll1 : polls) {
                    if (poll1.getName().equals(poll.getName())) {
                        modelAndView.addObject("namePoll", "This name is not avalible");
                        System.out.println("2");
                    } else {
                        pollService.save(poll);
                        System.out.println("3");
                    }
                }

      */
            pollService.save(poll);
            httpSession.setAttribute("pollCurrent",poll);
            modelAndView.setViewName("poll");
        }
        return modelAndView;
    }


}
