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

    public List<Group> getGroups() {
        return Repository.getGroups();
    }
}
