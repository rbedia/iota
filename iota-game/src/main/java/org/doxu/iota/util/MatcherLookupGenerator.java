package org.doxu.iota.util;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import org.doxu.iota.Card;
import org.doxu.iota.attr.Color;
import org.doxu.iota.attr.Count;
import org.doxu.iota.attr.Shape;

public class MatcherLookupGenerator {

    public static boolean validLot(List<Card> lot) {
        Collections.sort(lot, Card.SHAPE_COMPARATOR);
        boolean shapeDifferent = lot.get(0).getShape() == lot.get(1).getShape();
        for (int i = 2; i < lot.size(); i++) {
            if (lot.get(i - 1).getShape() == lot.get(i).getShape() && !shapeDifferent) {
                return false;
            }
            if (lot.get(i - 1).getShape() != lot.get(i).getShape() && shapeDifferent) {
                return false;
            }
        }
        return true;
    }

    public static Card card(Shape shape) {
        return new Card(Color.BLANK, shape, Count.BLANK);
    }

    public static void main(String[] args) {
        Queue<Boolean> queue = new ArrayDeque<>(8);
        for (Shape shape1 : Shape.real()) {
            for (Shape shape2 : Shape.real()) {
                for (Shape shape3 : Shape.real()) {
                    for (Shape shape4 : Shape.real()) {
                        boolean valid = validLot(Arrays.asList(card(shape1), card(shape2), card(shape3), card(shape4)));
                        queue.add(valid);
                        if (queue.size() == 8) {
                            printQueue(queue);
                        }
                    }
                }
            }
        }
    }

    private static void printQueue(Queue<Boolean> queue) {
        for (Boolean bool : queue) {
            System.out.print((bool ? " " : "") + bool + ", ");
        }
        System.out.println();
        queue.clear();
    }
}
