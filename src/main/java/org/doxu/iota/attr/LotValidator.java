package org.doxu.iota.attr;

/**
 *
 * @author rafael
 */
public class LotValidator {

    private static final boolean[] LOOKUP_3_CARD = {
        true, false, false, false, false, false, true, true,
        false, true, false, true, false, true, true, false,
        false, false, true, true, false, true, false, false,
        true, false, false, true, true, false, true, false,
        false, true, false, true, true, false, false, true,
        false, false, true, false, true, true, false, false,
        false, true, true, false, true, false, true, false,
        true, true, false, false, false, false, false, true
    };

    private static final boolean[] LOOKUP_4_CARD = {
        true, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false,
        false, false, false, true, false, false, true, false,
        false, false, false, false, false, false, false, true,
        false, false, false, false, false, true, false, false,
        false, false, false, false, false, false, true, false,
        false, true, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false,
        false, false, false, true, false, false, true, false,
        false, false, false, false, false, true, false, false,
        false, false, false, false, false, false, false, false,
        false, false, false, true, false, false, false, false,
        false, false, false, false, true, false, false, false,
        false, false, true, false, false, false, false, false,
        true, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, true,
        false, false, false, false, false, true, false, false,
        false, false, false, true, false, false, false, false,
        false, false, false, false, true, false, false, false,
        false, false, false, false, false, false, false, false,
        false, false, true, false, false, false, false, false,
        false, true, false, false, true, false, false, false,
        false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, true, false,
        false, true, false, false, false, false, false, false,
        false, false, true, false, false, false, false, false,
        true, false, false, false, false, false, false, false,
        false, true, false, false, true, false, false, false,
        false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, false,
        false, false, false, false, false, false, false, true
    };

    public static <T extends Attribute> boolean validate3(T a, T b, T c) {
        return LOOKUP_3_CARD[(a.val() << 4) + (b.val() << 2) + c.val()];
    }

    public static <T extends Attribute> boolean validate4(T a, T b, T c, T d) {
        return LOOKUP_4_CARD[(a.val() << 6) + (b.val() << 4) + (c.val() << 2) + d.val()];
    }
}
