package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String SQL_CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS userTable\n" +
            "(\n" +
            "    id       INT auto_increment,\n" +
            "    name     VARCHAR(20) null,\n" +
            "    lastName VARCHAR(20) null,\n" +
            "    age      BIT(16)        null,\n" +
            "    constraint userTable_pk\n" +
            "        primary key (id)\n" +
            ");";
    private static final String SQL_DROP_USERS_TABLE = "DROP TABLE IF EXISTS userTable";
    private static final String SQL_CLEAR_USERS_TABLE = "TRUNCATE TABLE userTable";

    public UserDaoHibernateImpl() {
    }

    public void createAnyRequestSQL(String sql) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            NativeQuery<?> nativeQuery = session.createNativeQuery(sql);
            nativeQuery.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void createUsersTable() {
        createAnyRequestSQL(SQL_CREATE_USERS_TABLE);
    }

    @Override
    public void dropUsersTable() {
        createAnyRequestSQL(SQL_DROP_USERS_TABLE);
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            User user = new User(null, name, lastName, age);
            session.persist(user);
            transaction.commit();
            session.close();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.remove(new User(id, "Хуйлуша", "ТвойБатя", (byte) 11));
            session.getTransaction().commit();
        } catch (HibernateException e) {
            transaction.rollback();
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        Session session = Util.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        try {
            userList = session.createQuery("SELECT i FROM User i", User.class).getResultList();
            userList.forEach(System.out::println);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            transaction.rollback();
        }
        return userList;
    }

    @Override
    public void cleanUsersTable() {
        createAnyRequestSQL(SQL_CLEAR_USERS_TABLE);
    }
}
