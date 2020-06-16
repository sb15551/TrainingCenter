package com.jp.trc.testing.view.action;

import com.jp.trc.testing.controller.UserController;
import com.jp.trc.testing.model.users.Student;
import com.jp.trc.testing.model.users.User;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * List of students sorted by rating.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 16.06.2020 20:35
 */
public class ViewRatingsAction implements UserAction {

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

    /**
     * Sorted students by rating.
     * @param students List of students to sort.
     */
    public static void sortStudetns(List<Student> students) {
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
