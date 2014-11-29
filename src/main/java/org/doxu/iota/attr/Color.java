package org.doxu.iota.attr;

/**
 *
 * @author rafael
 */
public enum Color implements Attribute {

    BLANK(-1), YELLOW(0), GREEN(1), RED(2), BLUE(3);

    private final int val;

    public static final Color[] VALID = {YELLOW, GREEN, RED, BLUE};

    Color(int val) {
        this.val = val;
    }

    @Override
    public int val() {
        return val;
    }

    public static Color fromChar(char in) {
        switch (in) {
            case ' ':
                return BLANK;
            case 'Y':
                return YELLOW;
            case 'G':
                return GREEN;
            case 'R':
                return RED;
            case 'B':
                return BLUE;
            default:
                throw new IllegalArgumentException("Invalid color.");
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case BLANK:
                return " ";
            case YELLOW:
                return "Y";
            case GREEN:
                return "G";
            case RED:
                return "R";
            case BLUE:
                return "B";
            default:
                throw new IllegalArgumentException();
        }
    }

}
