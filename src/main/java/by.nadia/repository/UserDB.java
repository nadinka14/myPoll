package by.nadia.repository;

import by.nadia.entity.Role;
import by.nadia.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDB {

    Connection connection;

    {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "TMS8");
        } catch (SQLException | ClassNotFoundException throwable) {
            throwable.printStackTrace();
        }
    }


    public boolean save(User user) {
        try {
             PreparedStatement preparedStatement = connection.prepareStatement("insert into users values ( default, ?, ?, ?, ? )");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.execute();


        } catch (SQLException throwable) {
            throwable.printStackTrace();
            return false;
        }
        return true;
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from users");
            while (resultSet.next()) {
                long id = resultSet.getLong(1);
                String username = resultSet.getString(2);
                String pass = resultSet.getString(3);
                String name = resultSet.getString(4);
                String email = resultSet.getString(5);
                User user = new User(id, username, pass, name, email, Role.USER);
                users.add(user);
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return users;
    }

    public User getById(long id) {
        User user = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                user = new User(resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        Role.USER);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    public User getByUsername(String username) {
        User user = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where username = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            user = new User(resultSet.getLong(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    Role.USER);

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return user;
    }

    public boolean contains(User user) {
        List<User> users = getAll();
        for (int i = 0; i < users.size(); i++) {
            if (user.equals(users.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(String username) {
        List<User> users = getAll();
        for (int i = 0; i < users.size(); i++) {
            if (username.equals(users.get(i).getUsername())) {
                return true;
            }
        }
        return false;
    }

    public String updateName(String newName, String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update users set name = ? where username = ?");
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return newName;
    }

    public String updatePassword(String pass, String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update users set password = ? where username = ?");
            preparedStatement.setString(1, pass);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return pass;
    }

    public String updateEmail(String email, String username) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update users set email = ? where username = ?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, username);
            preparedStatement.executeUpdate();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return email;
    }
}
