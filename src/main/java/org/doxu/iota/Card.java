package org.doxu.iota;

import java.util.Comparator;
import java.util.Objects;
import org.doxu.iota.attr.Color;
import org.doxu.iota.attr.Count;
import org.doxu.iota.attr.Shape;

/**
 *
 * @author rafael
 */
public class Card {

    private final Color color;

    private final Shape shape;

    private final Count count;

    public static final Card BLANK = new Card(Color.BLANK, Shape.BLANK, Count.BLANK);

    public static final Comparator<Card> SHAPE_COMPARATOR = new Comparator<Card>() {

        @Override
        public int compare(Card o1, Card o2) {
            return o1.getShape().compareTo(o2.getShape());
        }
    };

    public static final Comparator<Card> COLOR_COMPARATOR = new Comparator<Card>() {

        @Override
        public int compare(Card o1, Card o2) {
            return o1.getColor().compareTo(o2.getColor());
        }
    };

    public static final Comparator<Card> COUNT_COMPARATOR = new Comparator<Card>() {

        @Override
        public int compare(Card o1, Card o2) {
            return o1.getCount().compareTo(o2.getCount());
        }
    };

    public Card(Color color, Shape shape, Count count) {
        this.color = color;
        this.shape = shape;
        this.count = count;
    }

    public static Card create(String attributes) {
        if (attributes == null || attributes.length() != 3) {
            throw new IllegalArgumentException();
        }
        Color color = Color.fromChar(attributes.charAt(0));
        Shape shape = Shape.fromChar(attributes.charAt(1));
        Count count = Count.fromChar(attributes.charAt(2));
        return new Card(color, shape, count);
    }

    public int getPoints() {
        return getCount().points();
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, shape, count);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Card other = (Card) obj;
        if (this.color != other.color) {
            return false;
        }
        if (this.shape != other.shape) {
            return false;
        }
        if (this.count != other.count) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "" + color + shape + count;
    }

    public Color getColor() {
        return color;
    }

    public Shape getShape() {
        return shape;
    }

    public Count getCount() {
        return count;
    }

}
