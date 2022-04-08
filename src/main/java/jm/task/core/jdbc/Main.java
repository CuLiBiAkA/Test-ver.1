package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserService table = new UserServiceImpl();

        table.createUsersTable();
        table.cleanUsersTable();
        table.saveUser("Хуйлуша", "Хуйлов", (byte) 12);
        table.saveUser("Денчик", "Педро", (byte) 5);
        table.saveUser("Владос", "Неровный", (byte) 6);
        table.saveUser("Леха", "Рждшник", (byte) 7);
        table.removeUserById(1);
        table.getAllUsers();
        table.dropUsersTable();
    }
}
