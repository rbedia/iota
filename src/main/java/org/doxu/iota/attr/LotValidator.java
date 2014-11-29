package org.doxu.iota.attr;

/**
 *
 * @author rafael
 */
public class LotValidator {

    private static final boolean[] LOOKUP = {
        true,  false, false, false, false, false,  true,  true,
        false,  true, false,  true, false,  true,  true, false,
        false, false,  true,  true, false,  true, false, false,
        true,  false, false,  true,  true, false,  true, false,
        false,  true, false,  true,  true, false, false,  true,
        false, false,  true, false,  true,  true, false, false,
        false,  true,  true, false,  true, false,  true, false,
        true,   true, false, false, false, false, false,  true,
    };

    public static <T extends Attribute> boolean validate3(T a, T b, T c) {
//        System.out.println("" + a.val() + " " + b.val() + " " + c.val());
//        System.out.println("" + (a.val() << 4) + " " + (b.val() << 2) + " " + c.val());
        return LOOKUP[(a.val() << 4) + (b.val() << 2) + c.val()];
    }
}
