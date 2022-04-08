package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Statement statement = Util.connect().createStatement()) {
            String sql = "CREATE TABLE `test`.`test1` (\n" +
                    "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                    "  `name` VARCHAR(45) NULL,\n" +
                    "  `lastname` VARCHAR(45) NULL,\n" +
                    "  `age` BIT(16) NULL,\n" +
                    "  PRIMARY KEY (`id`),\n" +
                    "  UNIQUE INDEX `id_UNIQUE` (`id` ASC) VISIBLE)";
            statement.execute(sql);
            System.out.println("Таблица добавлена");
        } catch (Exception e) {
            System.out.println("Не удалось создать таблицу " + e);
        }
    }

    public void dropUsersTable() {
        try (Statement statement = Util.connect().createStatement()) {
            statement.execute("DROP TABLE  IF  EXISTS test1");
            System.out.println("Таблица удалена");
        } catch (Exception e) {
            System.out.println("Не удалось удалить таблицу " + e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        char s = 39;
        String sql = "INSERT INTO test1 (name,lastname,age)" +
                "VALUES" + "(" + s + name + s + "," + s + lastName + s + "," + s + age + s + ")";
        try (Statement statement = Util.connect().createStatement()) {
            statement.execute(sql);
            System.out.println("Юзер с именем - " + name + " добавлен в базу");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void removeUserById(long id) {
        char s = 39;
        String sql = "DELETE FROM test1 WHERE id = " + s + id + s;
        try (Statement statement = Util.connect().createStatement()) {
            statement.execute(sql);
            System.out.println("Юзер с id - " + id + " удален из базы");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<User> getAllUsers() {
        List<User> list= new ArrayList();
        try (Statement statement = Util.connect().createStatement()) {
            System.out.println("Вывожу узбеков");
            ResultSet resultSet = statement.executeQuery("SELECT * FROM test1");
            while (resultSet.next()) {
                User user = new User();
                user.setId((long) resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4));
                list.add(user);
                System.out.println(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cleanUsersTable() {
        try (Statement statement = Util.connect().createStatement()) {
            statement.execute("TRUNCATE TABLE test1");
            System.out.println("Таблица очищена");
        } catch (Exception e) {
            System.out.println("Не удалось очистить таблицу " + e);
        }
    }
}
