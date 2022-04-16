package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final String SQL_CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS Test\n" +
            "(\n" +
            "    id       INT auto_increment,\n" +
            "    name     VARCHAR(20) null,\n" +
            "    lastName VARCHAR(20) null,\n" +
            "    age      BIT(16)        null,\n" +
            "    constraint Test_pk\n" +
            "        primary key (id)\n" +
            ");";
    private static final String SQL_DROP_USERS_TABLE = "  DROP TABLE IF EXISTS test ";

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Session session = Util.connectionHibernate().openSession()) {
            session.beginTransaction();
            NativeQuery<?> nativeQuery = session.createNativeQuery(SQL_CREATE_USERS_TABLE);
            nativeQuery.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void dropUsersTable() {
        try (Session session = Util.connectionHibernate().openSession()) {
            session.beginTransaction();
            NativeQuery<?> nativeQuery = session.createNativeQuery(SQL_DROP_USERS_TABLE);
            nativeQuery.executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = Util.connectionHibernate().openSession()) {
            session.beginTransaction();
            User user = new User();
            user.setAge(age);
            user.setId(null);
            user.setLastName(lastName);
            user.setName(name);
            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = Util.connectionHibernate().openSession()) {
            session.beginTransaction();
            session.remove(session.get(User.class, id));
            session.flush();
            session.clear();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Session session = Util.connectionHibernate().openSession()) {
            session.beginTransaction();
            userList = session.createQuery("SELECT i FROM User i", User.class).getResultList();
            userList.forEach(System.out::println);
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return userList;
    }


    @Override
    public void cleanUsersTable() {
        try (Session session = Util.connectionHibernate().openSession()) {
            session.beginTransaction();

            final List<?> instances = session.createQuery("SELECT i FROM User i", User.class).getResultList();
            for (Object obj : instances) {
                session.delete(obj);
            }
            session.flush();
            session.clear();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }
}
