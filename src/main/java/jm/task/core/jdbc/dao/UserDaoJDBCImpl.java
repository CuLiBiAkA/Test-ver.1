package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    static String sqlCreateTable = "CREATE TABLE `test`.`test1` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NULL,\n" +
            "  `lastname` VARCHAR(45) NULL,\n" +
            "  `age` BIT(16) NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)";
    static String sqlDropUsersTable = "DROP TABLE  IF  EXISTS test1";
    static String sqlSaveUser = "INSERT INTO test1 (name,lastname,age)" +
            "VALUES" + "(?,?,?)";
    static String sqlRemoveUserById = "DELETE FROM test1 WHERE id = ";
    static String sqlGetAllUsers = "SELECT * FROM test1";
    static String sqlcleanUsersTable = "TRUNCATE TABLE test1";

    public UserDaoJDBCImpl() {
    }

    public void createUsersTable() {
        try (PreparedStatement statement = Util.connect().prepareStatement(sqlCreateTable)) {
            statement.execute();
            System.out.println("Таблица добавлена");
        } catch (Exception e) {
            System.out.println("Не удалось создать таблицу " + e);
        }
    }

    public void dropUsersTable() {
        try (PreparedStatement statement = Util.connect().prepareStatement(sqlDropUsersTable)) {
            statement.execute();
            System.out.println("Таблица удалена");
        } catch (Exception e) {
            System.out.println("Не удалось удалить таблицу " + e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = Util.connect().prepareStatement(sqlSaveUser)) {
            preparedStatement.setString(1,name);
            preparedStatement.setString(2,lastName);
            preparedStatement.setInt(3,age);
            preparedStatement.execute();
            System.out.println("Юзер с именем - " + name + " добавлен в базу");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void removeUserById(long id) {
        try (PreparedStatement statement = Util.connect().prepareStatement(sqlRemoveUserById + id)) {
            statement.execute();
            System.out.println("Юзер с id - " + id + " удален из базы");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList();
        try (PreparedStatement statement = Util.connect().prepareStatement(sqlGetAllUsers)) {
            System.out.println("Вывожу узбеков");
            ResultSet resultSet = statement.executeQuery(sqlGetAllUsers);
            while (resultSet.next()) {
                User user = new User((long) resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getByte(4));
                userList.add(user);
                System.out.println(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (PreparedStatement statement = Util.connect().prepareStatement(sqlcleanUsersTable)) {
            statement.execute();
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            System.out.println("Не удалось очистить таблицу " + e);
        }
    }
}
