package com.jp.trc.testing.view.input;

import com.jp.trc.testing.view.exception.MenuOutException;

import java.util.List;

/**
 * Class for validating entry of menu items.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 18.05.2020 10:05
*/
public class InputValidator implements Input {
    private final Input input;

    /**
     * Constructor for creating an object.
     * @param input Keyboard input.
     */
    public InputValidator(final Input input) {
        this.input = input;
    }

    /**
     * Ask for keyboard input.
     * @param question Keyboard input hint.
     * @return String entered from the keyboard.
     */
    @Override
    public String askItemMenu(String question) {
        return this.input.askItemMenu(question);
    }

    /**
     * Validates keyboard input.
     * @param question Keyboard input hint.
     * @param range List of menu item numbers.
     * @return Menu item number.
     */
    @Override
    public int ask(String question, List<Integer> range) {
        boolean invalid = true;
        int value = -1;
        do {
            try {
                value = this.input.ask(question, range);
                invalid = false;
            } catch (MenuOutException moe) {
                System.out.println(moe.getMessage());
            } catch (NumberFormatException nfe) {
                System.out.println("Введите корректные данные");
            }
        } while (invalid);
        return value;
    }

    /**
     * Validates keyboard input for subMenu.
     * @param question Keyboard input hint.
     * @param sizeMenu Size menu.
     * @param amountPage Amount page for subMenu.
     * @return Menu item value.
     */
    @Override
    public String askSubMenu(String question, int sizeMenu, int amountPage) {
        boolean invalid = true;
        String value = "";
        do {
            try {
                value = this.input.askSubMenu(question, sizeMenu, amountPage);
                invalid = false;
            } catch (MenuOutException moe) {
                System.out.println(moe.getMessage());
            } catch (NumberFormatException nfe) {
                System.out.println(nfe.getMessage());
            }
        } while (invalid);
        return value;
    }

    /**
     * Entering the response number when passing the test.
     * @param question Keyboard input hint.
     * @param size Size menu.
     * @return Menu item numbers.
     */
    @Override
    public int[] askNumberAnswer(String question, int size) {
        boolean invalid = true;
        int[] value = new int[size];
        do {
            try {
                value = this.input.askNumberAnswer(question, size);
                invalid = false;
            } catch (MenuOutException moe) {
                System.out.println("Введите ключ из диапазона меню");
            } catch (NumberFormatException nfe) {
                System.out.println("Введите корректные данные");
            }
        } while (invalid);
        return value;
    }
}
