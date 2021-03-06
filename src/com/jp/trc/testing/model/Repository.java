package com.jp.trc.testing.model;

import com.jp.trc.testing.model.tests.*;
import com.jp.trc.testing.model.users.Group;
import com.jp.trc.testing.model.users.Student;
import com.jp.trc.testing.model.users.Teacher;
import com.jp.trc.testing.model.users.User;

import java.util.*;

/**
 * Repository for storing users, tests, questions and answers to them.
 *
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
 */
public class Repository {

    /**
     * Users.
     */
    private static Map<String, User> users = new HashMap<>();

    /**
     * Groups students.
     */
    private static List<Group> groups = new ArrayList<>();

    /**
     * Tests.
     */
    private static List<Test> tests = new ArrayList<>();

    /**
     * Questions to tests .
     */
    private static List<Question> questions = new ArrayList<>();

    /**
     * Answers variants.
     */
    private static List<Answer> answers = new ArrayList<>();

    /**
     * Student assigned a test.
     */
    private static Set<Assignment> assignments = new HashSet<>();

    /**
     * Student answer to a question.
     */
    private static Set<AnswerToQuestion> answerToQuestions = new HashSet<>();

    /**
     * Adding group students.
     * @param group Groups students.
     * @return true, if group is added.
     */
    public static boolean addGroup(Group group) {
        return groups.add(group);
    }

    /**
     * Adding question to tests.
     * @param question
     * @return true, if question is added.
     */
    public static boolean addQuestion(Question question) {
        return questions.add(question);
    }

    /**
     * Adding answer to questions.
     * @param answer
     * @return true, if answer is added.
     */
    public static boolean addAnswer(Answer answer) {
        return answers.add(answer);
    }

    /**
     * Adding new user.
     * @param user User to add.
     * @return true, if user is added.
     */
    public static boolean addUser(User user) {
        if (existLogin(user.getLogin())) {
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
    public static boolean addTest(Test test) {
        return tests.add(test);
    }

    /**
     * Adds relationship "student-test".
     * @param assignment Relationship "student-test".
     * @return true, if assigment is added.
     */
    public static boolean addAssignment(Assignment assignment) {
        return assignments.add(assignment);
    }

    /**
     * Adds student answer to a question.
     * @param answerToQuestion Student answer to a question.
     * @return true, if answer to question added.
     */
    public static boolean addAnswerToQuestion(AnswerToQuestion answerToQuestion) {
        return answerToQuestions.add(answerToQuestion);
    }

    /**
     * Checks if such login exists.
     * @param login Login to check.
     * @return true, if such login exists.
     */
    private static boolean existLogin(String login) {
        return users.containsKey(login);
    }

    /**
     * Gets questions.
     *
     * @return value of questions java.util.List<com.jp.trc.testing.model.tests.Question>
     */
    public static List<Question> getQuestions() {
        return questions;
    }

    /**
     * Gets answers.
     *
     * @return value of answers java.util.List<com.jp.trc.testing.model.tests.Answer>
     */
    public static List<Answer> getAnswers() {
        return answers;
    }

    public Answer getAnswer(int answerId) {
        for (Answer answer : answers) {
            if (answer.getId() == answerId) {
                return answer;
            }
        }
        return null;
    }

    /**
     * Gets all users.
     *
     * @return value of users java.util.Map<java.lang.String,com.jp.trc.testing.model.users.User>
     */
    public static Map<String, User> getUsers() {
        return users;
    }

    /**
     * Gets user by id.
     * @param id User id.
     * @return Gets user by id.
     */
    public static User getUser(int id) {
        for (User user : users.values()) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    /**
     * Gets answerToQuestions.
     *
     * @return value of answerToQuestions java.util.Set<AnswerToQuestion>
     */
    public static Set<AnswerToQuestion> getAnswerToQuestions() {
        return answerToQuestions;
    }

    /**
     * Gets groups.
     *
     * @return value of groups java.util.List<com.jp.trc.testing.model.users.Group>
     */
    public static List<Group> getGroups() {
        return groups;
    }

    /**
     * Gets group by id.
     * @param id Group id.
     * @return Gets group by id.
     */
    public static Group getGroup(int id) {
        for (Group group : groups) {
            if (group.getId() == id) {
                return group;
            }
        }
        return null;
    }

    /**
     * Gets tests.
     *
     * @return value of tests java.util.List<com.jp.trc.testing.model.testing.Test>
     */
    public static List<Test> getTests() {
        return tests;
    }

    /**
     * Gets test by id.
     * @param id Test id.
     * @return Gets test by id.
     */
    public static Test getTest(int id) {
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
     * @return Gets tests author.
     */
    public static List<Test> getTestsAuthor(Teacher teacher) {
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
    public static List<Assignment> getAssignments() {
        List<Assignment> temp = new ArrayList<>(assignments);
        return temp;
    }

    /**
     * Get the test id assigned to the student.
     * @param studentId Student id.
     * @param testId Test id.
     * @return student-test attitude.
     */
    public static Assignment getAssignment(int studentId, int testId) {
        for (Assignment assignment : assignments) {
            if (assignment.getStudentId() == studentId && assignment.getTestId() == testId) {
                return assignment;
            }
        }
        return null;
    }

}
