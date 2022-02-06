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
                "age INTEGER NOT NULL);";
        try (Connection connection = connection()) {
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.executeUpdate();
            System.out.println("Table was created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String SQL = "DROP TABLE IF EXISTS users(" +
                "id serial," +
                "name VARCHAR(50)NOT NULL," +
                "lastName VARCHAR(50)NOT NULL," +
                "age INTEGER NOT NULL,"+
                "Primary Key(id));";
        try (Connection connection = connection()) {
            PreparedStatement statement = connection.prepareStatement(SQL);
            statement.executeUpdate(SQL);
            System.out.println("Table was droped");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String Save_SQL="INSERT INTO users(name,lastName,age)values (?,?,?);";
        try (Connection connection = connection()) {
            PreparedStatement psmt = connection.prepareStatement("Save_SQL");
            psmt.setString(1, user.getName());
            psmt.setString(2, user.getLastName());
            psmt.setByte(3, user.getAge());
            psmt.executeUpdate();
            System.out.println(user.getName() + " " + "was saved");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String remove_SQL="DELETE FROM users WHERE id=?";
        try (Connection connection = connection()) {
            PreparedStatement psmt = connection().prepareStatement(remove_SQL);
            psmt.setLong(1, id);
            psmt.executeUpdate();
            System.out.println(id + " " + "user was remove by id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        String sql = "SELECT * FROM users";
        List<User> userList = new ArrayList<>();
        try (Connection connect = connection();
             Statement statement = connect.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
                System.out.println(userList);
                System.out.println(resultSet.getString("name"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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