package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://192.168.1.12:3306/taskdb";
    private static final String USER = "db_admin";
    private static final String PASSWD = "Db123456789";

    static {
        loadDriver();
    }

    private static void loadDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Util() {
    }

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
