package com.jp.trc.testing.view.action;

import com.jp.trc.testing.controller.TestController;
import com.jp.trc.testing.model.tests.Test;
import com.jp.trc.testing.model.users.User;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Tests compiled by a teacher.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 16.06.2020 20:33
 */
public class ViewYourTestsAction implements UserAction {

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
