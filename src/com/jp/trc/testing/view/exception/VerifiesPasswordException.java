package com.jp.trc.testing.view.exception;

/**
 * Exception is thrown if the entered password does not match.
 * @author Surkov Aleksey (stibium128@gmail.com)
 * @date 03.07.2020 8:05
 */
public class VerifiesPasswordException extends Exception {

    /**
     * Constructor for creating an exception
     * @param msg Message to display.
     */
    public VerifiesPasswordException(String msg) {
        super(msg);
    }
}
