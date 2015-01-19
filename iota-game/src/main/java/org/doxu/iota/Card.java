package org.doxu.iota;

import java.util.Objects;
import org.doxu.iota.attr.Color;
import org.doxu.iota.attr.Count;
import org.doxu.iota.attr.Shape;

public class Card {

    private static final int ATTRIBUTE_COUNT = 3;

    private final Color color;

    private final Shape shape;

    private final Count count;

    public static final Card BLANK = new Card(Color.BLANK, Shape.BLANK, Count.BLANK);

    public Card(Color color, Shape shape, Count count) {
        this.color = color;
        this.shape = shape;
        this.count = count;
    }

    public static Card create(String attributes) {
        if (attributes == null || attributes.length() != ATTRIBUTE_COUNT) {
            throw new IllegalArgumentException();
        }
        Color color = Color.fromChar(attributes.charAt(0));
        Shape shape = Shape.fromChar(attributes.charAt(1));
        Count count = Count.fromChar(attributes.charAt(2));
        if (color == Color.BLANK && shape == Shape.BLANK && count == Count.BLANK) {
            return Card.BLANK;
        }
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
        return this.count == other.count;
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

    public boolean isBlank() {
        return equals(BLANK);
    }

}
