package com.jp.trc.testing.view.exception;

/**
 * Exception is thrown if such user not found.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 02.07.2020 15:02
 */
public class LoginExistsException extends Exception {

    /**
     * Constructor for creating an exception
     * @param msg Message to display.
     */
    public LoginExistsException(String msg) {
        super(msg);
    }
}
