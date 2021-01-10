package by.nadia.repository;

import by.nadia.entity.Poll;
import by.nadia.entity.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionDB {

    Connection connection;

    {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "TMS8");
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
    }

    public boolean save(Question question) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into questions values ( default, ?, ?,? )");
            preparedStatement.setLong(1, question.getIdPoll());
            preparedStatement.setLong(2, question.getNumber());
            preparedStatement.setString(3, question.getQuestion());
            preparedStatement.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Question> getAll() {
        List<Question> questions = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from questions");
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                long idPool = resultSet.getLong(2);
                long number=resultSet.getLong(3);
                String question=resultSet.getString(4);

                Question newQuestion= new Question(id,idPool,number,question);
                questions.add(newQuestion);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return questions;
    }

    public Question getById(long id) {
        Question question = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from questions where id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                question = new Question(resultSet.getLong(1),
                        resultSet.getLong(2),
                        resultSet.getLong(3),
                        resultSet.getString(4));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return question;
    }

    public List<Question> getByIdPoll(long idPoll) {
        List<Question> questions = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from questions where idpoll = ?");
            preparedStatement.setLong(1,idPoll);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                long idPool = resultSet.getLong(2);
                long number=resultSet.getLong(3);
                String question=resultSet.getString(4);

                Question newQuestion= new Question(id,idPool,number,question);
                questions.add(newQuestion);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return questions;
    }

    public boolean update(long id, long number,String question) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update users set number = ?, question=?  where id = ?");
            preparedStatement.setLong(1, number);
            preparedStatement.setString(2, question);
            preparedStatement.setLong(3, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return true;
    }
}
