package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String TASKDB_URL = "jdbc:mysql://192.168.1.12:3306/taskdb";
    private static final String USER = "db_admin";
    private static final String PASSWD = "Db123456789";

    private static SessionFactory sessionFactory;
    private static Session session;
    private static Transaction transaction;

    static {
        loadDriver();
    }

    private Util() {
    }

    private static void loadDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(TASKDB_URL, USER, PASSWD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static SessionFactory getSessionFactory(){
        if (sessionFactory == null) {
            try{
                Configuration configuration1 = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, TASKDB_URL);
                settings.put(Environment.USER, USER);
                settings.put(Environment.PASS, PASSWD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL8Dialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.HBM2DDL_AUTO, "update");
                configuration1.setProperties(settings);
                configuration1.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration1.getProperties()).build();
                sessionFactory = configuration1.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

    public static Session getSession() {
        return session;
    }

    public static Transaction getTransaction() {
        return transaction;
    }

    public static Session openSession() {
        return getSessionFactory().openSession();
    }

    public static void openTransactionSession() {
        session = openSession();
        transaction = session.beginTransaction();
//        return session;
    }

    public static void closeSession() {
        session.close();
    }

    public static void closeTransactionSession() {
        transaction.commit();
        closeSession();
    }

}
