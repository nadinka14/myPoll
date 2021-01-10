package by.nadia.service;

import by.nadia.entity.Answer;
import by.nadia.entity.Poll;
import by.nadia.entity.Question;
import by.nadia.repository.AnswerDB;
import by.nadia.repository.PollDB;
import by.nadia.repository.QuestionDB;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AnswerService {

    private AnswerDB answerDB=new AnswerDB();
    private QuestionDB questionDB=new QuestionDB();
    private PollDB pollDB=new PollDB();

    public boolean save(Answer answer) {
        return answerDB.save(answer);
    }

    public List<Answer> getAll() {
        return answerDB.getAll();
    }

    public Answer getById(long id) {
        return answerDB.getById(id);
    }

    public List<Answer> getAllByPollAndGuest(long idPoll, long idGuest) {
        List<Answer> answers=answerDB.getAll();
        List<Answer> answersByPollAndGuest=new ArrayList<>();
        for (Answer answer: answers) {
                if (answer.getIdGuest()==idGuest && answer.getIdPoll()==idPoll) {
                    answersByPollAndGuest.add(answer);
                }
            }
        return answersByPollAndGuest;
    }

    public Answer getAllByQuestionAndGuest(long idPoll,long number, long idGuest) {
        List<Answer> answers=answerDB.getAll();
        for (Answer answer: answers) {
            if (answer.getIdGuest()==idGuest && answer.getIdPoll()==idPoll && answer.getNumber()==number) {
                return answer;
            }
        }
        return null;
    }

}
