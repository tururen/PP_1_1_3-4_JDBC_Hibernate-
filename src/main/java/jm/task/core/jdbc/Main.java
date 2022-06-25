package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService service = new UserServiceImpl();
        service.createUsersTable();
        service.saveUser("Vasya", "Petrov", (byte)20);
        service.saveUser("Sanya", "Admin", (byte)42);
        service.saveUser("Elena", "Marinovna", (byte)30);
        System.out.println(service.getAllUsers());
        service.cleanUsersTable();
        service.dropUsersTable();
    }
}

