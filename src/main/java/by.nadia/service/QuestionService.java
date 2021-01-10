package by.nadia.service;

import by.nadia.entity.Question;
import by.nadia.repository.QuestionDB;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QuestionService {

    private QuestionDB questionDB=new QuestionDB();

    public boolean save(Question question) {
        return questionDB.save(question);
    }

    public List<Question> getAll() {
        return questionDB.getAll();
    }

    public Question getById(long id) {
        return questionDB.getById(id);
    }

    public List<Question> getByIdPoll(long idPoll) {
        return questionDB.getByIdPoll(idPoll);
    }

    public boolean update(long id, long number,String question) {
        return questionDB.update(id,number,question);
    }
}
