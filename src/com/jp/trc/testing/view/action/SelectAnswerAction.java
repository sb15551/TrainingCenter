package com.jp.trc.testing.view.action;

import com.jp.trc.testing.controller.TestController;
import com.jp.trc.testing.model.tests.Answer;
import com.jp.trc.testing.model.users.User;

/**
 * Saves the answer that the student noted during the test.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 17.06.2020 8:25
 */
public class SelectAnswerAction implements UserAction {

    /**
     * The answer that the student noted during the test.
     */
    private Answer answer;

    /**
     * Constructor for creating a object.
     * @param answer The answer that the student noted during the test.
     */
    public SelectAnswerAction(Answer answer) {
        this.answer = answer;
    }

    /**
     * Saves the answer that the student noted during the test.
     * @param user The user of this institution for whom the action is performed.
     */
    @Override
    public void execute(User user) {
        new TestController().addAnswerToQuestion(user.getId(), answer.getId());
    }
}
