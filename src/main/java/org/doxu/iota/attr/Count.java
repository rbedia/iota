package org.doxu.iota.attr;

public enum Count implements Attribute {

    BLANK(-1), ONE(0), TWO(1), THREE(2), FOUR(3);

    private final int val;

    public static final Count[] VALID = {ONE, TWO, THREE, FOUR};

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
                throw new IllegalArgumentException("Invalid count.");
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
}
