package com.jp.trc.testing.view.action;

import com.jp.trc.testing.controller.TestController;
import com.jp.trc.testing.model.Repository;
import com.jp.trc.testing.model.tests.Question;
import com.jp.trc.testing.model.tests.Test;
import com.jp.trc.testing.model.users.User;

import java.util.ArrayList;
import java.util.List;

/**
 * List of tests available to the user.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 16.06.2020 20:32
 */
public class ViewListTestsAction implements UserAction {

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
