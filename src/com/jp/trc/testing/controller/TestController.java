package com.jp.trc.testing.controller;

import com.jp.trc.testing.model.Repository;
import com.jp.trc.testing.model.tests.Assignment;
import com.jp.trc.testing.model.tests.Test;
import com.jp.trc.testing.model.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for working with tests.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 27.05.2020 9:20
 */
public class TestController {

    /**
     * List of tests available to the user.
     * @param studentId User id for which to get a list of tests.
     * @return Tests available to user.
     */
    public List<Test> getTestsToStudent(int studentId) {
        List<Test> tests = new ArrayList<>();
        List<Assignment> assignments = Repository.getAssignments().stream()
                .filter(test -> test.getStudentId() == studentId)
                .collect(Collectors.toList());

        for (Assignment assignment : assignments) {
            for (Test test : Repository.getTests()) {
                if (assignment.getTestId() == test.getId()) {
                    tests.add(test);
                }
            }
        }
        return tests;
    }

    /**
     * Tests compiled by a teacher.
     * @param teacherId Teacher id who compiled tests.
     * @return Tests compiled by a teacher.
     */
    public List<Test> getTestsByTeacher(int teacherId) {
        return Repository.getTests().stream()
                .filter(test -> test.getAuthor().getId() == teacherId)
                .collect(Collectors.toList());
    }

    /**
     * Tests results which compiled teacher.
     * @param teacherId Teacher id who compiled tests.
     * @return Test results for each student.
     */
    public List<Assignment> getResultTests(int teacherId) {
        List<Assignment> assignments = new ArrayList<>();
        for (Test test : Repository.getTests()) {
            if (test.getAuthor().getId() == teacherId) {
                for (Assignment assignment : Repository.getAssignments()) {
                    if (assignment.getTestId() == test.getId()) {
                        assignments.add(assignment);
                    }
                }
            }
        }
        return assignments;
    }
}
