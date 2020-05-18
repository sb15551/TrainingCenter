package com.jp.trc.testing.controller;

import com.jp.trc.testing.model.TrainingCenter;
import com.jp.trc.testing.model.testing.Assignment;
import com.jp.trc.testing.model.testing.Test;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.Input;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for users of type "Teacher".
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 9:28
 */
public class TeacherController {
    public static class ViewYourTests implements UserAction {

        /**
         * Tests compiled by a teacher.
         * @param center Institution in which the action is performed.
         * @param user The user of this institution for whom the action is performed.
         */
        @Override
        public void execute(TrainingCenter center, User user) {
            List<Test> yourTests = center.getTests().stream().filter(t -> t.getAuthor().equals(user))
                                                  .collect(Collectors.toList());
            System.out.printf("\t%-8s\t|\t\t%-16s\n", "Name test", "Author");
            yourTests.forEach(System.out::println);
        }
    }

    public static class ViewTestsResult implements UserAction {

        /**
         * Test results.
         * @param center Institution in which the action is performed.
         * @param user The user of this institution for whom the action is performed.
         */
        @Override
        public void execute(TrainingCenter center, User user) {
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
