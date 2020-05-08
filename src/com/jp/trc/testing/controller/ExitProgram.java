package com.jp.trc.testing.controller;

import com.jp.trc.testing.model.TrainingCenter;
import com.jp.trc.testing.model.users.User;
import com.jp.trc.testing.view.Input;

public class ExitProgram implements UserAction {
    @Override
    public void execute(Input input, TrainingCenter center, User user) {
        System.exit(0);
    }
}
