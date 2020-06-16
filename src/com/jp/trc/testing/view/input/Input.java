package com.jp.trc.testing.view.input;

import java.util.List;

/**
 * Keyboard input.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
 */
public interface Input {

    /**
     * Ask for keyboard input.
     * @param question Keyboard input hint.
     * @return String entered from the keyboard.
     */
    String askItemMenu(String question);

    /**
     * Validates keyboard input.
     * @param question Keyboard input hint.
     * @param range List of menu item numbers.
     * @return Menu item number.
     */
    int ask(String question, List<Integer> range);
}
