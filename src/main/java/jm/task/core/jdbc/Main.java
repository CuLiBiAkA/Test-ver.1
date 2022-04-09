package jm.task.core.jdbc;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;


public class Main {
    public static void main(String[] args) {
        UserService table = new UserServiceImpl();
        User user = new User();
        user.setAge((byte)10);
        user.setName("Хуйлуша");
        user.setLastName("Хуйлов");
        user.setId(1L);

        User user2 = new User();
        user2.setAge((byte)9);
        user2.setName("Денчик");
        user2.setLastName("Педро");
        user2.setId(2L);

        User user3 = new User();
        user3.setAge((byte)8);
        user3.setName("Шелуха");
        user3.setLastName("Чпушила");
        user3.setId(3L);

        User user4 = new User();
        user4.setAge((byte)7);
        user4.setName("Чмошник");
        user4.setLastName("Хуйлов");
        user4.setId(4L);

        table.createUsersTable();
        table.cleanUsersTable();
        table.saveUser(user.getName(), user.getLastName(), user.getAge());
        table.saveUser(user2.getName(), user.getLastName(), user.getAge());
        table.saveUser(user3.getName(), user.getLastName(), user.getAge());
        table.saveUser(user4.getName(), user.getLastName(), user.getAge());
        table.removeUserById(1);
        table.getAllUsers();
        table.dropUsersTable();
    }
}
