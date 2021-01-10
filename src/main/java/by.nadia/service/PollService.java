package by.nadia.service;

import by.nadia.entity.Poll;
import by.nadia.repository.PollDB;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PollService {

    private PollDB pollDB=new PollDB();

    public boolean save(Poll poll) {
        return pollDB.save(poll);
    }

    public List<Poll> getAll() {
        return pollDB.getAll();
    }

    public Poll getById(long id) {
        return pollDB.getById(id);
    }

    public Poll getByAuthorUsername(String username) {
        return pollDB.getByAuthorUsername(username);
    }

    public boolean delete(long id) {
        return pollDB.delete(id);
    }

    public String updateName(String newName, long id) {
        return pollDB.updateName(newName,id);
    }
}
