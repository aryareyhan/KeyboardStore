package main;

import java.util.ArrayList;

public class UserContainer {
    private ArrayList<User> users;

    private UserContainer() {
        users = new ArrayList<>();
    }

    private static UserContainer instance;

    public static UserContainer getInstance() {
        if (instance == null) {
            instance = new UserContainer();
        }
        return instance;
    }

    public void addUser(String email, String password) {
        User user = new User(email, password);
        users.add(user);
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
