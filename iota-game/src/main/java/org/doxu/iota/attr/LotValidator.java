package org.doxu.iota.attr;

import java.util.List;
import org.doxu.iota.Card;
import org.doxu.iota.IllegalLaydownException;

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

    public static void validate(List<Card> lot) throws IllegalLaydownException {
        // Lots of two or fewer cards are always valid
        if (lot.size() <= 2) {
            return;
        }
        // Lots of 5 or more cards are always invalid
        if (lot.size() > 4) {
            throw new IllegalLaydownException("A maximum of 4 cards can be in a lot.");
        }
        if (lot.size() == 3) {
            if (!validate3(lot.get(0).getShape(), lot.get(1).getShape(), lot.get(2).getShape())) {
                throw new IllegalLaydownException("Must be all the same or all different");
            }
            if (!validate3(lot.get(0).getColor(), lot.get(1).getColor(), lot.get(2).getColor())) {
                throw new IllegalLaydownException("Must be all the same or all different");
            }
            if (!validate3(lot.get(0).getCount(), lot.get(1).getCount(), lot.get(2).getCount())) {
                throw new IllegalLaydownException("Must be all the same or all different");
            }
        } else {
            if (!validate4(lot.get(0).getShape(), lot.get(1).getShape(), lot.get(2).getShape(), lot.get(3).getShape())) {
                throw new IllegalLaydownException("Must be all the same or all different");
            }
            if (!validate4(lot.get(0).getColor(), lot.get(1).getColor(), lot.get(2).getColor(), lot.get(3).getColor())) {
                throw new IllegalLaydownException("Must be all the same or all different");
            }
            if (!validate4(lot.get(0).getCount(), lot.get(1).getCount(), lot.get(2).getCount(), lot.get(3).getCount())) {
                throw new IllegalLaydownException("Must be all the same or all different");
            }
        }
    }

    public static <T extends Attribute> boolean validate3(T a, T b, T c) {
        return LOOKUP_3_CARD[(a.val() << 4) + (b.val() << 2) + c.val()];
    }

    public static <T extends Attribute> boolean validate4(T a, T b, T c, T d) {
        return LOOKUP_4_CARD[(a.val() << 6) + (b.val() << 4) + (c.val() << 2) + d.val()];
    }
}
