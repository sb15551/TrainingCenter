package com.jp.trc.testing.view.input;

import com.jp.trc.testing.view.exception.MenuOutException;

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

    /**
     * Validates keyboard input for subMenu.
     * @param question Keyboard input hint.
     * @param sizeMenu Size menu.
     * @param amountPage Amount page for subMenu.
     * @return Menu item value.
     * @throws MenuOutException Thrown out if the entered value does not fall into the menu dimension.
     * @throws NumberFormatException Thrown out if the entered value is not a number.
     */
    String askSubMenu(String question, int sizeMenu, int amountPage) throws MenuOutException, NumberFormatException;

    /**
     * Entering the response number when passing the test.
     * @param question Keyboard input hint.
     * @param size Size menu.
     * @return
     */
    int[] askNumberAnswer(String question, int size);
}
