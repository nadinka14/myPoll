package by.nadia.repository;

import by.nadia.entity.Poll;
import by.nadia.entity.Role;
import by.nadia.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PollDB {

    Connection connection;

    {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "TMS8");
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
    }

    public boolean save(Poll poll) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into polls values ( default, ?, ? )");
            preparedStatement.setString(1, poll.getName());
            preparedStatement.setString(2, poll.getAuthorUsername());
            preparedStatement.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }
        return true;
    }

    public List<Poll> getAll() {
        List<Poll> polls = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from polls");
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String name = resultSet.getString(2);
                String authorUsername = resultSet.getString(3);

                Poll poll=new Poll(id, name, authorUsername);
                polls.add(poll);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return polls;
    }

    public Poll getById(long id) {
        Poll poll = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from polls where id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                poll = new Poll(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return poll;
    }

    public Poll getByAuthorUsername(String username) {
        Poll poll = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from polls where author = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            poll = new Poll(resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return poll;
    }

    public boolean delete(long id) {
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement("delete from polls where id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
            connection.commit();
            connection.setAutoCommit(true);
            return true;
        } catch (SQLException throwable) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            throwable.printStackTrace();
        }
        return false;
    }

    public String updateName(String newName, long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update polls set name = ? where id = ?");
            preparedStatement.setString(1, newName);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return newName;
    }
}
