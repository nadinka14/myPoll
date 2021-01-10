package by.nadia.repository;

import by.nadia.entity.Answer;
import by.nadia.entity.Question;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnswerDB {

    Connection connection;

    {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "TMS8");
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
    }

    public boolean save(Answer answer) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into answers values ( default, ?, ?,?,?,?,? )");
            preparedStatement.setLong(1, answer.getIdQuestion());
            preparedStatement.setLong(2, answer.getIdPoll());
            preparedStatement.setLong(3, answer.getNumber());
            preparedStatement.setString(4, answer.getQuestion());
            preparedStatement.setLong(5, answer.getIdGuest());
            preparedStatement.setString(6, answer.getAnswer());


            preparedStatement.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Answer> getAll() {
        List<Answer> answers = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from answers");
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                long idQuestion = resultSet.getLong(2);
                long idPoll=resultSet.getLong(3);
                long number=resultSet.getLong(4);
                String question=resultSet.getString(5);
                long idGuest=resultSet.getLong(6);
                String answer=resultSet.getString(7);

                Answer newAnswer =new Answer(id, idQuestion,idPoll,number,question,idGuest, answer);
                answers.add(newAnswer);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return answers;
    }

    public Answer getById(long id) {
        Answer answer= null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from answers where id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                answer = new Answer(resultSet.getLong(1),
                        resultSet.getLong(2),
                        resultSet.getLong(3),
                        resultSet.getLong(4),
                        resultSet.getString(5),
                        resultSet.getLong(6),
                        resultSet.getString(7));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return answer;
    }


}
