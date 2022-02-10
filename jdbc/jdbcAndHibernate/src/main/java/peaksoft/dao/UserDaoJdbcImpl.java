package peaksoft.dao;

import peaksoft.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static peaksoft.util.Util.connection;

public abstract class UserDaoJdbcImpl implements UserDao {
    User user = new User();

    public UserDaoJdbcImpl() {
    }

    public void createUsersTable() {
        String SQL = "CREATE TABLE IF NOT EXISTS users(" +
                "id SERIAL," +
                "name VARCHAR(50)NOT NULL," +
                "lastName VARCHAR(50)NOT NULL," +
                "age INTEGER NOT NULL," +
                "PRIMARY KEY (id))";
        try (Connection connection = connection()) {
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.executeUpdate();
            System.out.println("Table was created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String SQL = "DROP TABLE IF EXISTS users";
//                "id serial," +
//                "name VARCHAR(50)NOT NULL," +
//                "lastName VARCHAR(50)NOT NULL," +
//                "age INTEGER NOT NULL,"+
//                "Primary Key(id));";
        try (Connection connection = connection()) {
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.executeUpdate();
            System.out.println("Table was droped");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String SAVE_SQL = "INSERT INTO users(name, lastname, age) VALUES (?, ?, ?)";
        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
            System.out.println(name + " " + "add to dataBase");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void removeUserById(long id) {
        String remove_SQL="DELETE FROM users WHERE id=?";
        try (Connection ignored = connection()) {
            PreparedStatement psmt = connection().prepareStatement(remove_SQL);
            psmt.setLong(1, id);
            psmt.executeUpdate();
            System.out.println(id + " " + "user was remove by id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String SQL = "SELECT * FROM users";
        List<User> userList = new ArrayList<>();
        try (Connection connect = connection();
             Statement statement = connect.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
                System.out.println(userList);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return userList;
    }

    public void cleanUsersTable() {
        String SQL = "truncate users";
        try (Connection connection = connection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(SQL);
            System.out.println("Table was cleaned");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}