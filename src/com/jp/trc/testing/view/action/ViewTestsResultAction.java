package com.jp.trc.testing.view.action;

import com.jp.trc.testing.controller.TestController;
import com.jp.trc.testing.model.Repository;
import com.jp.trc.testing.model.tests.Assignment;
import com.jp.trc.testing.model.users.User;

import java.util.List;

/**
 * Test results.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 16.06.2020 20:34
 */
public class ViewTestsResultAction implements UserAction {

    /**
     * Test results.
     * @param user The user of this institution for whom the action is performed.
     */
    @Override
    public void execute(User user) {
        List<Assignment> assignments = new TestController().getResultTests(user.getId());
        System.out.printf(
                "\t\t\t%-28s\t|\t%-20s\t|\t%s\n",
                "Full name of student",
                "Test name",
                "Test result"
        );
        assignments.forEach(s -> System.out.printf("%-40s\t|\t%-20s\t|\t%s\n",
                Repository.getUser(s.getStudentId()).getName(),
                Repository.getTest(s.getTestId()).getTitle(),
                s.getResult()));
    }
}
