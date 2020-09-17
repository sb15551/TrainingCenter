package com.jp.trc.testing.dao;

import com.jp.trc.testing.model.tests.Assignment;
import com.jp.trc.testing.model.users.Group;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.exception.LoginExistsException;
import com.jp.trc.testing.view.menu.Filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Database interface.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 04.09.2020 9:35
 */
public interface UserDAO {

    /**
     * Creating a user in the database
     * @param user The user to add to the database.
     * @throws LoginExistsException If user exist.
     */
    public void addUser(User user) throws LoginExistsException, IOException;

    /**
     * Get a user from the database by his id.
     * @param userId User id.
     * @return User from database.
     */
    public User getUser(int userId) throws Exception;

    /**
     * Get a group from the database by his id.
     * @param groupId Group id.
     * @return Group from database.
     */
    public Group getGroup(int groupId);

    /**
     * Updates the user in the database.
     * @param user User.
     */
    public void updateUser(User user);

    /**
     * Deletes the user in the database.
     * @param userId User id.
     */
    public void deleteUser(int userId);

    /**
     * Get all assignments.
     * @return List<Assignment> all tests assigned to students.
     */
    public List<Assignment> getAssignments();

    /**
     * Get all groups.
     * @param filter Filter for paging, search and ordering.
     * @return List<Groups> all student groups.
     */
    public List<Group> getGroups(Filter filter);

    /**
     * Gets all users.
     * @return List<User> All users from the database.
     */
    public Map<String, User> getUsers();

    /**
     * Does such a user exist in the database.
     * @param login Login to check.
     * @return true if login is finded.
     */
    public boolean userExist(String login);

    /**
     * Removes all data.
     */
    public void deleteAllData();
}
