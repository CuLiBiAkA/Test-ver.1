package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String URL = "jdbc:mysql://localhost:3307/test?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "culuc111653culuc";
    private static final String URL_HIBERNATE = "jdbc:mysql://localhost:3307/test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&relaxAutoCommit=false";
    private static final String USERNAME_HIBERNATE = "root";
    private static final String PASSWORD_HIBERNATE = "culuc111653culuc";


    private static Connection connection = createConnect();
    private static SessionFactory sessionFactory = createSessionFactory();

    public static Connection getConnection() {
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Connection createConnect() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                if (!connection.isClosed()) {
                    System.out.println("Соеденение с базой установлено");
                } else {
                    System.out.println("Соеденение с базой не установлено");
                }

            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            return connection;
        }
        return connection;
    }

    public static SessionFactory createSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                configuration
                        .setProperty("dialect", "org.hibernate.dialect.MySQLDialect")
                        .setProperty("hibernate.connection.url", URL_HIBERNATE)
                        .setProperty("hibernate.connection.username", USERNAME_HIBERNATE)
                        .setProperty("hibernate.connection.password", PASSWORD_HIBERNATE)
                        .setProperty("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver")
                        .setProperty("show_sql", "true")
                        .setProperty("hibernate.current_session_context_class", "thread");
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration
                        .addAnnotatedClass(User.class)
                        .buildSessionFactory(serviceRegistry);
            } catch (HibernateException e) {
                e.printStackTrace();
            }
        } else {
            return sessionFactory;
        }
        return sessionFactory;
    }
}