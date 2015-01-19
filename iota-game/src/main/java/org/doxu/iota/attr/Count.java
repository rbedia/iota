package org.doxu.iota.attr;

import java.util.Arrays;
import java.util.List;

public enum Count implements Attribute {

    BLANK(-1), ONE(0), TWO(1), THREE(2), FOUR(3);

    private final int val;

    private static final Count[] REAL = {ONE, TWO, THREE, FOUR};

    Count(int val) {
        this.val = val;
    }

    @Override
    public int val() {
        return val;
    }

    public int points() {
        return val + 1;
    }

    public static Count fromChar(char in) {
        switch (in) {
            case ' ':
            case '.':
                return BLANK;
            case '1':
                return ONE;
            case '2':
                return TWO;
            case '3':
                return THREE;
            case '4':
                return FOUR;
            default:
                throw new IllegalArgumentException("Invalid count: " + in);
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case BLANK:
                return " ";
            case ONE:
                return "1";
            case TWO:
                return "2";
            case THREE:
                return "3";
            case FOUR:
                return "4";
            default:
                throw new IllegalArgumentException();
        }
    }

    public static List<Count> real() {
        return Arrays.asList(REAL);
    }
}
