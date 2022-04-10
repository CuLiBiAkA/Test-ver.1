package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        createUserAndSave();
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }

    public static void createUserAndSave() {
        UserService userService = new UserServiceImpl();
        User user = new User(1L, "Chlen2", "Lisi2i", (byte) 5);
        userService.saveUser(user.getName(), user.getLastName(), user.getAge());
        User user2 = new User(1L, "Chlen3", "Lis3ii", (byte) 35);
        userService.saveUser(user2.getName(), user2.getLastName(), user2.getAge());
        User user3 = new User(1L, "4", "Lisii3", (byte) 52);
        userService.saveUser(user3.getName(), user3.getLastName(), user3.getAge());
        User user4 = new User(1L, "Chlen5", "Lisi3i", (byte) 54);
        userService.saveUser(user4.getName(), user4.getLastName(), user4.getAge());
    }
}
