package by.nadia.service;

import by.nadia.entity.User;
import by.nadia.repository.UserDB;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {

    private UserDB userDB=new UserDB();

    public boolean save (User user){
        List<User> users=userDB.getAll();
        for (User userDB: users) {
            if(userDB.getUsername().equals(user.getUsername())){
            return false;
            }
        }
        return userDB.save(user);
    }
    public List<User> getAll() {
        return userDB.getAll();
    }

    public User getById(long id) {
        return userDB.getById(id);
    }

    public  User getByUsername(String username){
        return userDB.getByUsername(username);
    }

    public boolean contains(User user) {
        return userDB.contains(user);
    }

    public boolean contains(String username) {
        return userDB.contains(username);
    }

    public String updateName(String newName, String username) {
        return userDB.updateName(newName,username);
    }

    public String updatePassword(String pass, String username) {
        return userDB.updatePassword(pass, username);
    }

    public String updateEmail(String email, String username) {
        return userDB.updateEmail(email, username);
    }
}
