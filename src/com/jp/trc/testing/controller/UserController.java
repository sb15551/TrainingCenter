package com.jp.trc.testing.controller;

import com.jp.trc.testing.dao.CSVUserDAO;
import com.jp.trc.testing.dao.UserDAO;
import com.jp.trc.testing.model.tests.Assignment;
import com.jp.trc.testing.model.users.Group;
import com.jp.trc.testing.model.users.Student;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.exception.LoginExistsException;
import com.jp.trc.testing.view.exception.ObjectNotFoundException;
import com.jp.trc.testing.view.exception.VerifiesPasswordException;
import com.jp.trc.testing.view.menu.Filter;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Controller for working with users of the institution.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 27.05.2020 8:40
 */
public class UserController {

    private UserDAO userDAO = new CSVUserDAO();

    /**
     * List of all users.
     * @param filter Filter for paging, search and ordering.
     * @return ist of all users.
     */
    public List<User> getAllUsers(Filter filter) {
        List<User> tmp = new ArrayList<>(userDAO.getUsers().values());
        Collections.sort(tmp, filter.getComparator());
        if (filter.getLimit() == 0) {
            return tmp;
        } else {
            return tmp.stream()
                    .skip(filter.getOffset())
                    .limit(filter.getLimit())
                    .collect(Collectors.toList());
        }
    }

    /**
     * Get user by login.
     * @param login User login.
     * @return user by login.
     * @throws ObjectNotFoundException Exception is thrown if sought-for user not found.
     */
    public User getUser(String login) throws ObjectNotFoundException {
        Map<String, User> users = userDAO.getUsers();
        if (!users.containsKey(login)) {
            throw new ObjectNotFoundException("Such user not found!!!");
        }
        return users.get(login);
    }

    /**
     * Get user by id.
     * @param userId Sought-for user id.
     * @return user by id.
     * @throws ObjectNotFoundException Exception is thrown if sought-for user not found.
     */
    public User getUser(int userId) throws ObjectNotFoundException {
        try {
            return userDAO.getUser(userId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new ObjectNotFoundException("Such user not found!!!");
    }

    /**
     * Does such a login exist in the database.
     * @param login Login to check.
     * @return true if login is finded.
     * @throws LoginExistsException
     */
    public boolean existsLogin(String login) throws LoginExistsException {
        if (!userDAO.userExist(login)) {
            throw new LoginExistsException("Такого логина не существует!\n"
                    + "Введите заново: ");
        }
        return true;
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
        if (!userDAO.getUsers().get(login).getPassword().equals(password)) {
            throw new VerifiesPasswordException("Пароль не верный!!!\n"
                    + "Введите еще раз: ");
        }
        return userDAO.getUsers().get(login).getPassword().equals(password);
    }

    /**
     * Calculates the student student rating.
     * @param studentId Id of the student for which the rating is calculated.
     */
    public void calculateStudentRating(int studentId) throws Exception {
        List<Assignment> testsStudent = userDAO.getAssignments().stream()
                .filter(assignment -> assignment.getStudentId() == studentId
                        && assignment.getResult() != null)
                .collect(Collectors.toList());
        double rating = testsStudent.stream().mapToInt(Assignment::getResult).sum();
        rating /= testsStudent.size();
        Student student = (Student) userDAO.getUser(studentId);
        student.setRating(rating);
        userDAO.updateUser(student);
    }

    /**
     * Get list groups.
     * @param filter Filter for paging, search and ordering.
     * @return List<Group> list groups.
     */
    public List<Group> getGroups(Filter filter) {
        return userDAO.getGroups(filter);
    }

    /**
     * Get group by id.
     * @param groupId Group id.
     * @return
     */
    public Group getGroup(int groupId) {
        return userDAO.getGroup(groupId);
    }

    /**
     * Get amount group students.
     * @param groupId Group id.
     * @return count students in group, if (groupId == 0) return count all students.
     */
    public int getAmountGroupStudents(int groupId) {
        return getGroupStudents(groupId, new Filter(0, 0, Comparator.naturalOrder())).size();
    }

    /**
     * Get list students in group.
     * @param groupId Group ID.
     * @param filter Filter for paging, search and ordering.
     * @return List student group.
     */
    public List<Student> getGroupStudents(int groupId, Filter filter) {
        List<Student> tmp;
        if (groupId == 0) {
            tmp = getAllStudents(filter);
            Collections.sort(tmp, filter.getComparator());
            return tmp;
        }

        List<Student> students = new ArrayList<>();
        tmp = getAllStudents(new Filter(0, 0, filter.getComparator()));
        Collections.sort(tmp, filter.getComparator());

        if (filter.getLimit() == 0) {
            students.addAll(
                    tmp.stream()
                            .filter(u -> u.getGroupId() == groupId)
                            .collect(Collectors.toList())
            );
        } else {
            students.addAll(
                    tmp.stream()
                            .filter(u -> u.getGroupId() == groupId)
                            .skip(filter.getOffset())
                            .limit(filter.getLimit())
                            .collect(Collectors.toList())
            );
        }

        if (students.size() == 0) {
            throw new ObjectNotFoundException("Group is empty!!!");
        } else {
            return students;
        }
    }

    /**
     * Get list all students.
     * @param filter Filter for paging, search and ordering.
     * @return list all students.
     */
    public List<Student> getAllStudents(Filter filter) {
        List<User> tmp = getAllUsers(new Filter(0, 0, Comparator.naturalOrder()));
        Collections.sort(tmp, filter.getComparator());
        if (filter.getLimit() == 0) {
            return new ArrayList(
                    tmp.stream()
                            .filter(u -> u instanceof Student)
                            .collect(Collectors.toList())
            );
        } else {
            return new ArrayList(
                    tmp.stream()
                            .filter(u -> u instanceof Student)
                            .skip(filter.getOffset())
                            .limit(filter.getLimit())
                            .collect(Collectors.toList())
            );
        }
    }

    /**
     * Searches for a group by its name.
     * @param filter Filter for paging, search and ordering.
     * @return List<Group> found groups.
     */
    public List<Group> searchGroup(Filter filter) {
        return getGroups(new Filter(0, 0, Comparator.naturalOrder())).stream()
                .filter(g -> g.getTitle().contains(filter.getSearchPhrase()))
                .skip(filter.getOffset())
                .limit(filter.getLimit())
                .collect(Collectors.toList());
    }

    /**
     * Looking for a student by group.
     * @param groupId Group in which search student.
     * @param filter Filter for paging, search and ordering.
     * @return
     */
    public List<Student> searchStudentInGroup(int groupId, Filter filter) {
        return getGroupStudents(groupId, filter).stream()
                .filter(s -> s.getName().contains(filter.getSearchPhrase()))
                .skip(filter.getOffset())
                .limit(filter.getLimit())
                .collect(Collectors.toList());
    }

    /**
     * Search for a users by name.
     * @param filter Filter for paging, search and ordering.
     * @return List<User> Searched users.
     */
    public List<User> searchUser(Filter filter) {
        return getAllUsers(new Filter(0, 0, Comparator.naturalOrder())).stream()
                .filter(s -> s.getName().contains(filter.getSearchPhrase()))
                .skip(filter.getOffset())
                .limit(filter.getLimit())
                .collect(Collectors.toList());
    }
}
