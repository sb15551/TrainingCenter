package com.jp.trc.testing.view;

import com.jp.trc.testing.controller.TestController;
import com.jp.trc.testing.controller.UserController;
import com.jp.trc.testing.model.Repository;
import com.jp.trc.testing.model.tests.Assignment;
import com.jp.trc.testing.model.tests.Question;
import com.jp.trc.testing.model.tests.Test;
import com.jp.trc.testing.model.users.Student;
import com.jp.trc.testing.model.users.User;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Class for creating a menu item event.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 29.05.2020 8:53
 */
public class MenuAction {

    /**
     * List of tests available to the user.
     */
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

    /**
     * Tests compiled by a teacher.
     */
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

    /**
     * Test results.
     */
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

    /**
     * Lists of all users of this institution.
     */
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

    /**
     * List of students sorted by rating.
     */
    public static class ViewRatingsAction implements UserAction {

        /**
         * List of students sorted by rating.
         * @param user The user of this institution for whom the action is performed.
         */
        @Override
        public void execute(User user) {
            List<User> users = new UserController().getAllUsers();
            List<Student> students = new ArrayList(
                    users.stream()
                            .filter(u -> u instanceof Student)
                            .collect(Collectors.toList())
            );
            sortStudetns(students);
            students.forEach(System.out::println);
        }
    }

    /**
     * Group of students sorted by rating.
     */
    public static class RatingStudentsByGroup implements UserAction {

        /**
         * Group id.
         */
        private int groupId;

        /**
         * Submenu with groups.
         */
        private SubMenu subMenu;

        /**
         * Constructor for creating a object.
         */
        public RatingStudentsByGroup(User user) {
            subMenu = new SubMenu(user, this.getClass().getSimpleName());
        }

        /**
         * Constructor for creating a object.
         * @param groupId
         */
        public RatingStudentsByGroup(int groupId) {
            this.groupId = groupId;
        }

        /**
         * Group of students sorted by rating.
         * @param user The user of this institution for whom the action is performed.
         */
        @Override
        public void execute(User user) {
            if (groupId == 0) {
                subMenu.show();
            } else {
                List<User> users = new UserController().getAllUsers();
                List<Student> students = new ArrayList(
                        users.stream()
                                .filter(u -> u instanceof Student && ((Student) u).getGroupId() == groupId)
                                .collect(Collectors.toList())
                );
                sortStudetns(students);
                students.forEach(System.out::println);
            }
        }
    }

    /**
     * Sorted students by rating.
     * @param students List of students to sort.
     */
    private static void sortStudetns(List<Student> students) {
        for (int i = 0; i < students.size() - 1; i++) {
            for (int j = students.size() - 1; j > i; j--) {
                if (students.get(j - 1).getRating() > students.get(j).getRating()) {
                    Student tmp = students.get(j - 1);
                    students.set(j - 1, students.get(j));
                    students.set(j, tmp);
                }
            }
        }
    }
}
