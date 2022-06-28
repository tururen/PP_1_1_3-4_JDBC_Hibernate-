package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import java.util.List;

import static jm.task.core.jdbc.util.Util.*;


public class UserDaoHibernateImpl implements UserDao {

    private static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS user " +
            "(id BIGINT NOT NULL AUTO_INCREMENT, " +
            "name VARCHAR(30) NOT NULL, " +
            "lastname VARCHAR(30) NOT NULL, " +
            "age TINYINT NOT NULL, PRIMARY KEY (id))";

    private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS user";
    private static final String CLEAN_USERS_TABLE_SQL = "TRUNCATE TABLE user";


    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        try {
            openTransactionSession();
            Session session = getSession();
            session.createSQLQuery(CREATE_TABLE_SQL).executeUpdate();
            closeTransactionSession();
        } catch (Exception e){
            getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropUsersTable() {
        try {
            openTransactionSession();
            Session session = getSession();
            session.createSQLQuery(DROP_TABLE_SQL).executeUpdate();
            closeTransactionSession();
        } catch (Exception e) {
            getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            openTransactionSession();
            Session session = getSession();
            session.save(new User(name, lastName, age));
            closeTransactionSession();
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (Exception e){
            getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try {
            openTransactionSession();
            Session session = getSession();
            session.delete(session.get(User.class, id));
            session.flush();
            closeTransactionSession();
        } catch (Exception e) {
            getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (var session = openSession()){
            List<User> list = session.createQuery("select u from User u", User.class).getResultList();
            return list;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void cleanUsersTable() {
        try {
            openTransactionSession();
            Session session = getSession();
            session.createSQLQuery(CLEAN_USERS_TABLE_SQL).executeUpdate();
            closeTransactionSession();

        } catch (Exception e){
            getTransaction().rollback();
            throw new RuntimeException(e);
        }
    }
}
