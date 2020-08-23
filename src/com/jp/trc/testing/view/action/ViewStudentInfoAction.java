package com.jp.trc.testing.view.action;

import com.jp.trc.testing.controller.UserController;
import com.jp.trc.testing.model.users.Student;
import com.jp.trc.testing.model.users.User;

/**
 * Displays information about the student.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 16.07.2020 12:52
 */
public class ViewStudentInfoAction implements UserAction {

    private Student student;

    /**
     * Constructor for creating a object.
     * @param student
     */
    public ViewStudentInfoAction(Student student) {
        this.student = student;
    }

    @Override
    public void execute(User user, int page) {
        UserController userController = new UserController();
        System.out.printf(
                "Name: %s\n"
                + "Age: %s\n"
                + "Group: %s\n"
                + "Rating: %s\n",
                student.getName(),
                student.getAge(),
                userController.getGroup(student.getGroupId()),
                Double.isNaN(student.getRating())
                        ? "Студент еще не прошел ни одного теста"
                        : String.format("%.1f", student.getRating())
        );

    }
}
