package com.jp.trc.testing.model.users;

import com.jp.trc.testing.model.Repository;

import java.util.Locale;

/**
 * User type "Student".
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
 */
public class Student extends User {

    /**
     * Id of the group that the student belongs to.
     */
    private int groupId;

    /**
     * Student rating.
     */
    private double rating;

    /**
     * Constructor for creating a user.
     * @param id User id.
     * @param groupId Id of the group that the student belongs to.
     * @param name Full name user.
     * @param login Login user.
     * @param password Password user.
     * @param age Age user.
     */
    public Student(int id, int groupId, String name, String login, String password, int age) {
        super(id, name, login, password, age);
        this.groupId = groupId;
    }

    /**
     * Gets groupId.
     *
     * @return value of groupId int
     */
    public int getGroupId() {
        return groupId;
    }

    /**
     * Sets value groupId.
     *
     * @param groupId value of groupId
     */
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    /**
     * Gets rating.
     *
     * @return value of rating double
     */
    public double getRating() {
        return rating;
    }

    /**
     * Sets value rating.
     *
     * @param rating value of rating
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * Returns a string representation of the object in format "Name | Group | Rating".
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return String.format(
                Locale.US,
                "%-40s\t|\t%-10s\t|\t%.1f",
                super.getName(),
                Repository.getGroup(groupId),
                rating
        );
    }
}
