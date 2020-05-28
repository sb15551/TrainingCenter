package com.jp.trc.testing.controller;

import com.jp.trc.testing.model.Institute;
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

    public static class ViewListTestsAction implements UserAction {

        /**
         * List of tests available to the user.
         * @param center Institution in which the action is performed.
         * @param user The user of this institution for whom the action is performed.
         */
        @Override
        public void execute(Institute center, User user) {
            center.getTests().forEach(s -> System.out.printf("Тест \"%s\" - Number questions: %s\n", s.getTitle(), s.getQuestions().size()));
        }
    }

    public static class ViewYourTestsAction implements UserAction {

        /**
         * Tests compiled by a teacher.
         * @param center Institution in which the action is performed.
         * @param user The user of this institution for whom the action is performed.
         */
        @Override
        public void execute(Institute center, User user) {
            List<Test> yourTests = center.getTests().stream().filter(t -> t.getAuthor().equals(user))
                    .collect(Collectors.toList());
            System.out.printf("\t%-8s\t|\t\t%-16s\n", "Name test", "Author");
            yourTests.forEach(System.out::println);
        }
    }

    public static class ViewTestsResultAction implements UserAction {

        /**
         * Test results.
         * @param center Institution in which the action is performed.
         * @param user The user of this institution for whom the action is performed.
         */
        @Override
        public void execute(Institute center, User user) {
            List<Assignment> assignments = new ArrayList<>();
            for (Test test : center.getTests()) {
                if (test.getAuthor().equals(user)) {
                    for (Assignment assignment : center.getAssignments()) {
                        if (assignment.getTestId() == test.getId()) {
                            assignments.add(assignment);
                        }
                    }
                }
            }
            System.out.printf("\t\t\t%-28s\t|\t%-20s\t|\t%s\n", "Full name of student", "Test name", "Test result");
            assignments.forEach(s -> System.out.printf("%-40s\t|\t%-20s\t|\t%s\n",
                    center.getUser(s.getStudentId()).getName(),
                    center.getTest(s.getTestId()).getTitle(),
                    s.getResult()));
        }
    }
}
