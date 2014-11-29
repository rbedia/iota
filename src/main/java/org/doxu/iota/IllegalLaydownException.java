package org.doxu.iota;

/**
 *
 * @author rafael
 */
public class IllegalLaydownException extends Exception {

    /**
     * Creates a new instance of <code>IllegalLaydownException</code> without
     * detail message.
     */
    public IllegalLaydownException() {
    }

    /**
     * Constructs an instance of <code>IllegalLaydownException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public IllegalLaydownException(String msg) {
        super(msg);
    }
}
