package com.jp.trc.testing.model;

import com.jp.trc.testing.model.testing.Assignment;
import com.jp.trc.testing.model.testing.Test;
import com.jp.trc.testing.model.users.Teacher;
import com.jp.trc.testing.model.users.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Object of this class is created once and passed to methods.
 * In him stores users and tests.
 *
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
 */
public class TrainingCenter {

    /**
     * Users.
     */
    private Map<String, User> users;

    /**
     * Tests.
     */
    private List<Test> tests;

    /**
     * Student assigned a test.
     */
    private List<Assignment> assignments;

    public TrainingCenter() {
        users = new HashMap<>();
        tests = new ArrayList<>();
        assignments = new ArrayList<>();
    }

    /**
     * Adding new user.
     * @param user User to add.
     * @return true, if user is added.
     */
    public boolean addUser(User user) {
        if (existLogin(user.getLogin())){
            System.out.println("Пользователь с таким логином уже существует!!!");
            return false;
        }
        users.put(user.getLogin(), user);
        return true;
    }

    /**
     * Adding new test.
     * @param test Test to add.
     * @return true, if test is added.
     */
    public boolean addTest(Test test) {
        return tests.add(test);
    }

    /**
     * Checks if such login exists.
     * @param login Login to check.
     * @return true, if such login exists
     */
    private boolean existLogin(String login) {
        return users.containsKey(login);
    }

    /**
     * Gets users.
     *
     * @return value of users java.util.Map<java.lang.String,com.jp.trc.testing.model.users.User>
     */
    public Map<String, User> getUsers() {
        return users;
    }

    /**
     * Gets user by id.
     * @param id User id.
     * @return
     */
    public User getUser(int id) {
        for (User user : users.values()) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    /**
     * Gets tests.
     *
     * @return value of tests java.util.List<com.jp.trc.testing.model.testing.Test>
     */
    public List<Test> getTests() {
        return tests;
    }

    /**
     * Gets test by id.
     * @param id Test id.
     * @return
     */
    public Test getTest(int id){
        for (Test test : tests) {
            if (test.getId() == id) {
                return test;
            }
        }
        return null;
    }

    /**
     * Gets tests author.
     * @param teacher
     * @return
     */
    public List<Test> getTestsAuthor(Teacher teacher) {
        List<Test> temp = new ArrayList<>();
        for (Test test : tests) {
            if (test.getAuthor().equals(teacher)) {
                temp.add(test);
            }
        }
        return temp;
    }

    /**
     * Gets assignments.
     *
     * @return value of assignments java.util.List<com.jp.trc.testing.model.testing.Assignment>
     */
    public List<Assignment> getAssignments() {
        List<Assignment> temp = new ArrayList<>(assignments);
        return temp;
    }

}
