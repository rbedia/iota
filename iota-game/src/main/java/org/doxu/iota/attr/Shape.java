package org.doxu.iota.attr;

import java.util.Arrays;
import java.util.List;

public enum Shape implements Attribute {

    BLANK(-1), CIRCLE(0), SQUARE(1), TRIANGLE(2), CROSS(3);

    private final int val;

    private static final Shape[] REAL = {CIRCLE, SQUARE, TRIANGLE, CROSS};

    Shape(int val) {
        this.val = val;
    }

    @Override
    public int val() {
        return val;
    }

    public static Shape fromChar(char in) {
        switch (in) {
            case ' ':
            case '.':
                return BLANK;
            case 'O':
                return CIRCLE;
            case 'S':
                return SQUARE;
            case 'T':
                return TRIANGLE;
            case '+':
                return CROSS;
            default:
                throw new IllegalArgumentException("Invalid shape: " + in);
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case BLANK:
                return " ";
            case CIRCLE:
                return "O";
            case SQUARE:
                return "S";
            case TRIANGLE:
                return "T";
            case CROSS:
                return "+";
            default:
                throw new IllegalArgumentException();
        }
    }

    public static List<Shape> real() {
        return Arrays.asList(REAL);
    }
}
