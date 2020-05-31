package com.jp.trc.testing.view;

import com.jp.trc.testing.controller.TestController;
import com.jp.trc.testing.controller.UserController;
import com.jp.trc.testing.model.Repository;
import com.jp.trc.testing.model.tests.Assignment;
import com.jp.trc.testing.model.tests.Question;
import com.jp.trc.testing.model.tests.Test;
import com.jp.trc.testing.model.users.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 29.05.2020 8:53
 */
public class MenuAction {
    public static class ViewListTestsAction implements UserAction {

        /**
         * List of tests available to the user.
         * @param user The user of this institution for whom the action is performed.
         */
        @Override
        public void execute(User user) {
            List<Test> tests = new TestController().getTestsToStudent(user.getId());
            List<Question> questions = new ArrayList<>(Repository.getQuestions());

            for (Test test : tests) {
                long size = questions.stream().filter(q -> test.getId() == q.getId())
                        .count();
                System.out.printf("Тест \"%s\" - Quentity questions: %s\n", test.getTitle(), size);
            }
        }
    }

    public static class ViewYourTestsAction implements UserAction {

        /**
         * Tests compiled by a teacher.
         * @param user The user of this institution for whom the action is performed.
         */
        @Override
        public void execute(User user) {
            List<Test> tests = new TestController().getTestsByTeacher(user.getId());
            List<Test> yourTests = tests.stream().filter(t -> t.getAuthor().equals(user))
                    .collect(Collectors.toList());
            System.out.printf("\t%-8s\t|\t\t%-16s\n", "Name test", "Author");
            yourTests.forEach(System.out::println);
        }
    }

    public static class ViewTestsResultAction implements UserAction {

        /**
         * Test results.
         * @param user The user of this institution for whom the action is performed.
         */
        @Override
        public void execute(User user) {
            List<Assignment> assignments = new TestController().getResultTests(user.getId());
            System.out.printf("\t\t\t%-28s\t|\t%-20s\t|\t%s\n", "Full name of student", "Test name", "Test result");
            assignments.forEach(s -> System.out.printf("%-40s\t|\t%-20s\t|\t%s\n",
                    Repository.getUser(s.getStudentId()).getName(),
                    Repository.getTest(s.getTestId()).getTitle(),
                    s.getResult()));
        }
    }

    public static class ViewListUsersAction implements UserAction {

        /**
         * Lists of all users of this institution.
         * @param user The user of this institution for whom the action is performed.
         */
        @Override
        public void execute(User user) {
            List<User> users = new UserController().getAllUsers();
            Collections.sort(users, Comparator.naturalOrder());
            System.out.printf("\t\t\t%-28s\t|\t%-10s\t|\t%-10s\t|\t%s\t|\t%s\n", "Full name user", "Login", "Password", "Age", "Type");
            users.forEach(System.out::println);
        }
    }
}
