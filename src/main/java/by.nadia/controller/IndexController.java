package by.nadia.controller;


import by.nadia.dto.AnswerDTO;
import by.nadia.entity.Answer;
import by.nadia.entity.Question;
import by.nadia.entity.Poll;
import by.nadia.entity.User;
import by.nadia.entity.Role;
import by.nadia.service.AnswerService;
import by.nadia.service.PollService;
import by.nadia.service.QuestionService;
import by.nadia.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    private PollService pollService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private AnswerService answerService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ModelAndView homePage(ModelAndView modelAndView, HttpSession httpSession){
        List<Poll> all = pollService.getAll();
        httpSession.setAttribute("allPolls", all);
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @PostMapping(path = "/showPolls")
    public ModelAndView showPoll(ModelAndView modelAndView, HttpSession httpSession) {
        List<Poll> polls = pollService.getAll();
        httpSession.setAttribute("allPolls", polls);
        modelAndView.setViewName("poll");
        return modelAndView;
    }

    @GetMapping(path = "/answer")
    public ModelAndView getAnswer(@RequestParam long id, ModelAndView modelAndView, HttpSession httpSession) {
        Poll poll=pollService.getById(id);
        modelAndView.addObject("pollAnswer", poll);
        List<Question> questions=questionService.getByIdPoll(id);

        List<User> users=userService.getAll();
     //   System.out.println("size="+users.size());
        long lastId=users.get(users.size()-1).getId();
      //  System.out.println("lastId="+lastId);
        User guest=new User(lastId+1,"guest"+(lastId+1),"guest","guest","", Role.GUEST);
        userService.save(guest);

        List<User> usersCurrent=userService.getAll();
        for (User u:usersCurrent) {
            if(u.getUsername().equals(guest.getUsername())){
                guest.setId(u.getId());
            }
        }
     //  System.out.println("newId="+usersCurrent.get(usersCurrent.size()-1).getId());


        AnswerDTO answersDTO  = new AnswerDTO();

    //    System.out.println(lastId);
        List<Answer> answers=new ArrayList<>();
        for (int i=0;i<questions.size();i++) {
            Answer answer=new Answer(questions.get(i).getId(),id,questions.get(i).getNumber(),
                    questions.get(i).getQuestion(),guest.getId(),"");
            answers.add(i,answer);

            answersDTO.addAnswer(answer);
        }
        httpSession.setAttribute("answersOfPoll",answers);

        modelAndView.addObject("answersDTO", answersDTO);
        httpSession.setAttribute("answersDTO1", answers);



        modelAndView.setViewName("answer");
        return modelAndView;
    }

    @PostMapping(path = "/sendAnswers")
    public ModelAndView sendAnswer(@ModelAttribute AnswerDTO answersDTO, ModelAndView modelAndView, BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("answer");
        } else {

            List<Answer> answers1= (ArrayList) httpSession.getAttribute("answersDTO1");
            List<Answer> answers= answersDTO.getAnswers();
            for (int i=0;i<answers.size();i++) {
                answers.get(i).setId(answers1.get(i).getId());
                answers.get(i).setIdQuestion(answers1.get(i).getIdQuestion());
                answers.get(i).setIdPoll(answers1.get(i).getIdPoll());
                answers.get(i).setNumber(answers1.get(i).getNumber());
                answers.get(i).setQuestion(answers1.get(i).getQuestion());
                answers.get(i).setIdGuest(answers1.get(i).getIdGuest());

                answerService.save(answers.get(i));
            }

            httpSession.setAttribute("answers", answerService.getAll());

        }
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping(path = "/showAnswers")
    public ModelAndView showAnswers(ModelAndView modelAndView, HttpSession httpSession) {
        modelAndView.setViewName("answerByUser");
        return modelAndView;
    }

    @PostMapping(path = "/showAnswers")
    public ModelAndView showAnswersByUsersPolls(ModelAndView modelAndView, HttpSession httpSession) {
        User user= (User) httpSession.getAttribute("user");
        List<Poll> allPolls=pollService.getAll();
        List<Poll> polls=new ArrayList<>();
        for (Poll poll: allPolls) {
            if(poll.getAuthorUsername().equals(user.getUsername())){
                polls.add(poll);

            }
        }

        List<Answer> allAnswers=answerService.getAll();
        List<Answer> guestAnswers =new ArrayList<>();
        for (int j=0;j<polls.size();j++) {
        for (int i=0;i<allAnswers.size();i++) {
            if(polls.get(j).getId()==allAnswers.get(i).getIdPoll()){
                guestAnswers.add(allAnswers.get(i));
            }
            }
        }
        for (int i=0;i< guestAnswers.size()-1;i++) {
            if( guestAnswers.get(i).getIdGuest()< guestAnswers.get(i+1).getIdGuest()){
                Answer temp= guestAnswers.get(i);
                guestAnswers.add(i, guestAnswers.get(i+1));
                guestAnswers.add(i+1,temp);
            }
        }
        modelAndView.addObject("answerByUsersPoll", guestAnswers);
        modelAndView.setViewName("answerByUser");
        return modelAndView;
    }
}
