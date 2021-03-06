package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private static final String SQL_CREATE_TABLE = "CREATE TABLE `test`.`userTable` (\n" +
            "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
            "  `name` VARCHAR(45) NULL,\n" +
            "  `lastname` VARCHAR(45) NULL,\n" +
            "  `age` BIT(16) NULL,\n" +
            "  PRIMARY KEY (`id`),\n" +
            "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)";
    private static final String SQL_DROP_USERS_TABLE = "DROP TABLE  IF  EXISTS userTable";
    private static final String SQL_SAVE_USER = "INSERT INTO userTable (name,lastname,age)" +
            "VALUES" + "(?,?,?)";
    private static final String SQL_REMOVE_USER_BY_ID = "DELETE FROM test1 WHERE id = ";
    private static final String SQL_GET_ALL_USERS = "SELECT * FROM userTable";
    private static final String SQL_CLEAN_USERS_TABLE = "TRUNCATE TABLE userTable";

    public UserDaoJDBCImpl() {
    }

    public void createAnyRequestSQL(String sql) {
        try (PreparedStatement statement = Util.getConnection().prepareStatement(sql)) {
            statement.execute();
            System.out.println("Операция выполнена");
        } catch (SQLException e) {
            System.out.println("Не удалось выполнить операцию" + e);
        }
    }

    public void createUsersTable() {
        createAnyRequestSQL(SQL_CREATE_TABLE);
    }

    public void dropUsersTable() {
        createAnyRequestSQL(SQL_DROP_USERS_TABLE);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (PreparedStatement preparedStatement = Util.getConnection().prepareStatement(SQL_SAVE_USER)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.execute();
            System.out.println("Юзер с именем - " + name + " добавлен в базу");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void removeUserById(long id) {
        try (PreparedStatement statement = Util.getConnection().prepareStatement(SQL_REMOVE_USER_BY_ID + id)) {
            statement.execute();
            System.out.println("Юзер с id - " + id + " удален из базы");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (PreparedStatement statement = Util.getConnection().prepareStatement(SQL_GET_ALL_USERS)) {
            System.out.println("Вывожу узбеков");
            ResultSet resultSet = statement.executeQuery(SQL_GET_ALL_USERS);
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
        try (PreparedStatement statement = Util.getConnection().prepareStatement(SQL_CLEAN_USERS_TABLE)) {
            statement.execute();
            System.out.println("Таблица очищена");
        } catch (SQLException e) {
            System.out.println("Не удалось очистить таблицу " + e);
        }
    }
}
