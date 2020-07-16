package com.jp.trc.testing.controller;

import com.jp.trc.testing.model.Repository;
import com.jp.trc.testing.model.tests.Assignment;
import com.jp.trc.testing.model.users.Group;
import com.jp.trc.testing.model.users.Student;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.exception.LoginExistsException;
import com.jp.trc.testing.view.exception.ObjectNotFoundException;
import com.jp.trc.testing.view.exception.VerifiesPasswordException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for working with users of the institution.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 27.05.2020 8:40
 */
public class UserController {

    /**
     * List of all users.
     * @return List of all users.
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(Repository.getUsers().values());
    }

    /**
     * Get user by login.
     * @param login User login.
     * @return user by login.
     * @throws ObjectNotFoundException Exception is thrown if sought-for user not found.
     */
    public User getUser(String login) throws ObjectNotFoundException {
        if (!Repository.getUsers().containsKey(login)) {
            throw new ObjectNotFoundException("Such user not found!!!");
        }
        return Repository.getUsers().get(login);
    }

    /**
     * Get user by id.
     * @param userId Sought-for user id.
     * @return user by id.
     * @throws ObjectNotFoundException Exception is thrown if sought-for user not found.
     */
    public User getUser(int userId) throws ObjectNotFoundException {
        User searchedUser = null;
        for (User user : Repository.getUsers().values()) {
            if (user.getId() == userId) {
                searchedUser = user;
            }
        }
        if (searchedUser == null) {
            throw new ObjectNotFoundException("Such user not found!!!");
        } else {
            return searchedUser;
        }
    }

    /**
     * Does such a login exist in the database.
     * @param login Login to check.
     * @return true if login is finded.
     * @throws LoginExistsException
     */
    public boolean existsLogin(String login) throws LoginExistsException {
        if (!Repository.getUsers().containsKey(login)) {
            throw new LoginExistsException("Такого логина не существует!\n"
                    + "Введите заново: ");
        }
        return Repository.getUsers().containsKey(login);
    }

    /**
     * Verifies user password.
     * @param login User login for which password is to be verified.
     * @param password Password to check.
     * @return true if the password matches.
     * @throws VerifiesPasswordException
     */
    public boolean verifiesPassword(String login, String password)
            throws VerifiesPasswordException {
        if (!Repository.getUsers().get(login).getPassword().equals(password)) {
            throw new VerifiesPasswordException("Пароль не верный!!!\n"
                    + "Введите еще раз: ");
        }
        return Repository.getUsers().get(login).getPassword().equals(password);
    }

    /**
     * Calculates the student student rating.
     * @param studentId Id of the student for which the rating is calculated.
     */
    public void calculateStudentRating(int studentId) {
        List<Assignment> testsStudent = Repository.getAssignments().stream()
                .filter(assignment -> assignment.getStudentId() == studentId
                        && assignment.getResult() != null)
                .collect(Collectors.toList());
        double rating = testsStudent.stream().mapToInt(Assignment::getResult).sum();
        rating /= testsStudent.size();
        Student student = (Student) Repository.getUser(studentId);
        student.setRating(rating);
    }

    /**
     * Get list groups.
     * @return List<Group> list groups.
     */
    public List<Group> getGroups() {
        return Repository.getGroups();
    }

    /**
     * Get group by id.
     * @param groupId Group id.
     * @return
     */
    public Group getGroup(int groupId) {
        Group group = Repository.getGroup(groupId);
        if (group == null) {
            throw new ObjectNotFoundException("Such group not found!!!");
        } else {
            return group;
        }

    }

    /**
     * Get list students in group.
     * @param groupId Group ID.
     * @return List student group.
     */
    public List<Student> getGroupStudents(int groupId) {
        if (Repository.getGroup(groupId) == null) {
            throw new ObjectNotFoundException("Such group not found!!!");
        }

        List<Student> students = new ArrayList(
                getAllUsers().stream()
                        .filter(u -> u instanceof Student
                                && ((Student) u).getGroupId() == groupId)
                        .collect(Collectors.toList())
        );
        if (students.size() == 0) {
            throw new ObjectNotFoundException("Group is empty!!!");
        } else {
            return students;
        }
    }

    /**
     * Get list all students.
     * @return list all students.
     */
    public List<Student> getAllStudents() {
        return new ArrayList(
                getAllUsers().stream()
                        .filter(u -> u instanceof Student)
                        .collect(Collectors.toList())
        );
    }
}
