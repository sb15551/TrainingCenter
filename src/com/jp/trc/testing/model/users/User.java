package com.jp.trc.testing.model.users;

import java.util.Objects;

/**
 * User creation template.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
 */
public abstract class User implements Comparable<User> {

    /**
     * User id.
     */
    private int id;

    /**
     * Full name user.
     */
    private String name;

    /**
     * Login user.
     */
    private String login;

    /**
     * Password user.
     */
    private String password;

    /**
     * Age user.
     */
    private int age;

    public User(int id, String name, String login, String password, int age) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.age = age;
    }

    /**
     * Gets id.
     *
     * @return value of id int
     */
    public int getId() {
        return id;
    }

    /**
     * Gets name.
     *
     * @return value of name java.lang.String
     */
    public String getName() {
        return name;
    }

    /**
     * Sets value name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets login.
     *
     * @return value of login java.lang.String
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets value login.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets password.
     *
     * @return value of password java.lang.String
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets value password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets age.
     *
     * @return value of age int
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets value age.
     */
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("%-40s\t|\t%-10s\t|\t%-10s\t|\t%s\t|\t%s", name, login, password, age, this.getClass().getSimpleName());
    }

    @Override
    public int compareTo(User o) {
        return this.name.compareTo(o.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
