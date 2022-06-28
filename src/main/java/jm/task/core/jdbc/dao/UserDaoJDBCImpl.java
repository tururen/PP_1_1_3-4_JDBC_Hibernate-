package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static jm.task.core.jdbc.util.Util.*;

public class UserDaoJDBCImpl implements UserDao {

    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS user " +
            "(id BIGINT NOT NULL AUTO_INCREMENT, " +
            "name VARCHAR(30) NOT NULL, " +
            "lastname VARCHAR(30) NOT NULL, " +
            "age TINYINT NOT NULL, PRIMARY KEY (id))";

    private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS user";
    private static final String REMOVE_USER_BY_ID_SQL = "DELETE FROM User WHERE id = ?";
    private static final String SAVE_USER_SQL = "INSERT INTO user (name, lastname, age) VALUES (?, ?, ?)";
    private static final String CLEAN_USERS_TABLE_SQL = "TRUNCATE TABLE user";
    private static final String GET_ALL_USERS_SQL = "SELECT id, name, lastname, age FROM user";

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (var connection = Util.getConnection();
             var statement = connection.createStatement()) {

            statement.execute(CREATE_TABLE_SQL);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        try (var connection = Util.getConnection();
             var statement = connection.createStatement()) {

            statement.execute(DROP_TABLE_SQL);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (var connection = Util.getConnection();
             var preparedStatement = connection.prepareStatement(SAVE_USER_SQL)) {

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
            System.out.println("User с именем - " + name + " добавлен в базу данных");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try (var connection = Util.getConnection();
             var preparedStatement = connection.prepareStatement(REMOVE_USER_BY_ID_SQL)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        try (var connection = Util.getConnection();
             var preparedStatement = connection.prepareStatement(GET_ALL_USERS_SQL)) {

            List<User> AllUsers = new ArrayList<>();
            var resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                AllUsers.add(user);
            }
            return AllUsers;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        try (var connection = Util.getConnection();
             var statement = connection.createStatement()) {

            statement.execute(CLEAN_USERS_TABLE_SQL);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
