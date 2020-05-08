package com.jp.trc.testing.model.users;

import java.util.Objects;

public abstract class User implements Comparable<User> {
    private int id;
    private String name;
    private String login;
    private String password;
    private int age;

    public User(int id, String name, String login, String password, int age) {
        this.id = id;
        this.name = name;
        this.login = login;
        this.password = password;
        this.age = age;
    }

    // -------------------------------- Getter and setter --------------------------------


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

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
