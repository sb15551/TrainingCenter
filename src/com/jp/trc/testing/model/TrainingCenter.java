package com.jp.trc.testing.model;

import com.jp.trc.testing.model.testing.Subscription;
import com.jp.trc.testing.model.testing.Testing;
import com.jp.trc.testing.model.users.Teacher;
import com.jp.trc.testing.model.users.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TrainingCenter {
    private Map<String, User> users;
    private List<Testing> tests;
    private List<Subscription> subscriptions;

    public TrainingCenter() {
        users = new HashMap<>();
        tests = new ArrayList<>();
        subscriptions = new ArrayList<>();
    }

    public boolean addUser(User user) {
        if (existLogin(user.getLogin())){
            System.out.println("Пользователь с таким логином уже существует!!!");
            return false;
        }
        users.put(user.getLogin(), user);
        return true;
    }

    public boolean addTest(Testing test) {
        return tests.add(test);
    }

    private boolean existLogin(String login) {
        return users.containsKey(login);
    }

    public void addSubscription(Subscription subscription) {
        subscriptions.add(subscription);
    }

    // --------------------------------- Getters and Setters ---------------------------------

    public Map<String, User> getUsers() {
        Map<String, User> temp = new HashMap<>(users);
        return temp;
    }

    public User getUser(int id) {
        for (User user : users.values()) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public List<Testing> getTests() {
        List<Testing> temp = new ArrayList<>(tests);
        return temp;
    }

    public Testing getTest(int id){
        for (Testing test : tests) {
            if (test.getId() == id) {
                return test;
            }
        }
        return null;
    }

    public List<Testing> getTestsAuthors(Teacher teacher) {
        List<Testing> temp = new ArrayList<>();
        for (Testing test : tests) {
            if (test.getAuthor().equals(teacher)) {
                temp.add(test);
            }
        }
        return temp;
    }

    public List<Subscription> getSubscriptions() {
        List<Subscription> temp = new ArrayList<>(subscriptions);
        return temp;
    }

}
