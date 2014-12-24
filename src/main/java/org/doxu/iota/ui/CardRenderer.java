package org.doxu.iota.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import org.doxu.iota.Card;

public class CardRenderer {

    public static final int CARD_WIDTH = 24;

    public static final int INSET = 2;
    private static final int BOTTOM_OFFSET = 2;
    private static final int OFFSET = 2;
    private static final int DOT_WIDTH = 3;
    private static final int CIRCLE_INSET = 4;
    private static final int SQUARE_INSET = 6;
    private static final int CROSS_WIDTH = 8;

    private static final Color OUTLINE_COLOR = new Color(0, 0, 0);
    private static final Color DOT_COLOR = new Color(0, 0, 0);
    private static final Color CARD_COLOR = new Color(255, 255, 255);
    private static final Color BLUE_COLOR = new Color(150, 200, 255);
    private static final Color GREEN_COLOR = new Color(144, 209, 64);
    private static final Color RED_COLOR = new Color(255, 100, 100);
    private static final Color YELLOW_COLOR = new Color(243, 247, 12);
    private static final Color WHITE_COLOR = new Color(255, 255, 255);

    private static final GeneralPath CROSS_PATH = new GeneralPath();

    private static final GeneralPath TRIANGLE_PATH = new GeneralPath();

    static {
        // top
        CROSS_PATH.moveTo(CARD_WIDTH / 2 - CROSS_WIDTH / 2, INSET);
        CROSS_PATH.lineTo(CARD_WIDTH / 2 + CROSS_WIDTH / 2, INSET);
        // right top inside
        CROSS_PATH.lineTo(CARD_WIDTH / 2 + CROSS_WIDTH / 2, CARD_WIDTH / 2 - CROSS_WIDTH / 2);
        // right
        CROSS_PATH.lineTo(CARD_WIDTH - INSET, CARD_WIDTH / 2 - CROSS_WIDTH / 2);
        CROSS_PATH.lineTo(CARD_WIDTH - INSET, CARD_WIDTH / 2 + CROSS_WIDTH / 2);
        // right bottom inside
        CROSS_PATH.lineTo(CARD_WIDTH / 2 + CROSS_WIDTH / 2, CARD_WIDTH / 2 + CROSS_WIDTH / 2);
        // bottom
        CROSS_PATH.lineTo(CARD_WIDTH / 2 + CROSS_WIDTH / 2, CARD_WIDTH - INSET);
        CROSS_PATH.lineTo(CARD_WIDTH / 2 - CROSS_WIDTH / 2, CARD_WIDTH - INSET);
        // left bottom inside
        CROSS_PATH.lineTo(CARD_WIDTH / 2 - CROSS_WIDTH / 2, CARD_WIDTH / 2 + CROSS_WIDTH / 2);
        // left
        CROSS_PATH.lineTo(INSET, CARD_WIDTH / 2 + CROSS_WIDTH / 2);
        CROSS_PATH.lineTo(INSET, CARD_WIDTH / 2 - CROSS_WIDTH / 2);
        // left top inside
        CROSS_PATH.lineTo(CARD_WIDTH / 2 - CROSS_WIDTH / 2, CARD_WIDTH / 2 - CROSS_WIDTH / 2);
        CROSS_PATH.closePath();

        TRIANGLE_PATH.moveTo(CARD_WIDTH / 2, INSET);
        TRIANGLE_PATH.lineTo(INSET, CARD_WIDTH - INSET - BOTTOM_OFFSET);
        TRIANGLE_PATH.lineTo(CARD_WIDTH - INSET, CARD_WIDTH - INSET - BOTTOM_OFFSET);
        TRIANGLE_PATH.closePath();
    }

    public static void draw(Graphics g, Card card) {
        drawBackground(g);
        drawShape(g, card);
        drawDots(g, card);
    }

    private static void drawBackground(Graphics g) {
        g.setColor(CARD_COLOR);
        g.fillRect(INSET, INSET, CARD_WIDTH - INSET * 2, CARD_WIDTH - INSET * 2);
        g.setColor(OUTLINE_COLOR);
        g.drawRect(INSET, INSET, CARD_WIDTH - INSET * 2, CARD_WIDTH - INSET * 2);
    }

    private static void drawDots(Graphics g, Card card) {
        g.setColor(DOT_COLOR);
        switch (card.getCount()) {
            case ONE:
                g.fillRect(CARD_WIDTH / 2 - DOT_WIDTH / 2, CARD_WIDTH / 2 - DOT_WIDTH / 2, DOT_WIDTH, DOT_WIDTH);
                break;
            case TWO:
                g.fillRect(CARD_WIDTH / 2 - DOT_WIDTH / 2 - OFFSET, CARD_WIDTH / 2 - DOT_WIDTH / 2, DOT_WIDTH, DOT_WIDTH);
                g.fillRect(CARD_WIDTH / 2 - DOT_WIDTH / 2 + OFFSET, CARD_WIDTH / 2 - DOT_WIDTH / 2, DOT_WIDTH, DOT_WIDTH);
                break;
            case THREE:
                g.fillRect(CARD_WIDTH / 2 - DOT_WIDTH / 2, CARD_WIDTH / 2 - DOT_WIDTH / 2 - OFFSET, DOT_WIDTH, DOT_WIDTH);
                g.fillRect(CARD_WIDTH / 2 - DOT_WIDTH / 2 - OFFSET, CARD_WIDTH / 2 - DOT_WIDTH / 2 + OFFSET, DOT_WIDTH, DOT_WIDTH);
                g.fillRect(CARD_WIDTH / 2 - DOT_WIDTH / 2 + OFFSET, CARD_WIDTH / 2 - DOT_WIDTH / 2 + OFFSET, DOT_WIDTH, DOT_WIDTH);
                break;
            case FOUR:
                g.fillRect(CARD_WIDTH / 2 - DOT_WIDTH / 2 - OFFSET, CARD_WIDTH / 2 - DOT_WIDTH / 2 - OFFSET, DOT_WIDTH, DOT_WIDTH);
                g.fillRect(CARD_WIDTH / 2 - DOT_WIDTH / 2 + OFFSET, CARD_WIDTH / 2 - DOT_WIDTH / 2 - OFFSET, DOT_WIDTH, DOT_WIDTH);
                g.fillRect(CARD_WIDTH / 2 - DOT_WIDTH / 2 - OFFSET, CARD_WIDTH / 2 - DOT_WIDTH / 2 + OFFSET, DOT_WIDTH, DOT_WIDTH);
                g.fillRect(CARD_WIDTH / 2 - DOT_WIDTH / 2 + OFFSET, CARD_WIDTH / 2 - DOT_WIDTH / 2 + OFFSET, DOT_WIDTH, DOT_WIDTH);
                break;
            case BLANK:
            default:
                break;
        }
    }

    private static void drawShape(Graphics g, Card card) {
        Color fillColor = getColor(card);
        switch (card.getShape()) {
            case CIRCLE:
                drawCircle(g, fillColor);
                break;
            case SQUARE:
                drawSquare(g, fillColor);
                break;
            case CROSS:
                drawCross((Graphics2D) g, fillColor);
                break;
            case TRIANGLE:
                drawTriangle((Graphics2D) g, fillColor);
                break;
            case BLANK:
            default:
                break;
        }
    }

    private static void drawCircle(Graphics g, Color fillColor) {
        g.setColor(fillColor);
        g.fillOval(CIRCLE_INSET, CIRCLE_INSET, CARD_WIDTH - CIRCLE_INSET * 2, CARD_WIDTH - CIRCLE_INSET * 2);
        g.setColor(OUTLINE_COLOR);
        g.drawOval(CIRCLE_INSET, CIRCLE_INSET, CARD_WIDTH - CIRCLE_INSET * 2, CARD_WIDTH - CIRCLE_INSET * 2);
    }

    private static void drawSquare(Graphics g, Color fillColor) {
        g.setColor(fillColor);
        g.fillRect(SQUARE_INSET, SQUARE_INSET, CARD_WIDTH - SQUARE_INSET * 2, CARD_WIDTH - SQUARE_INSET * 2);
        g.setColor(OUTLINE_COLOR);
        g.drawRect(SQUARE_INSET, SQUARE_INSET, CARD_WIDTH - SQUARE_INSET * 2, CARD_WIDTH - SQUARE_INSET * 2);
    }

    private static void drawCross(Graphics2D g, Color fillColor) {
        g.setColor(fillColor);
        g.fill(CROSS_PATH);
        g.setColor(OUTLINE_COLOR);
        g.draw(CROSS_PATH);
    }

    private static void drawTriangle(Graphics2D g, Color fillColor) {
        g.setColor(fillColor);
        g.fill(TRIANGLE_PATH);
        g.setColor(OUTLINE_COLOR);
        g.draw(TRIANGLE_PATH);
    }

    private static Color getColor(Card card) {
        Color color;
        switch (card.getColor()) {
            case BLUE:
                color = BLUE_COLOR;
                break;
            case GREEN:
                color = GREEN_COLOR;
                break;
            case RED:
                color = RED_COLOR;
                break;
            case YELLOW:
                color = YELLOW_COLOR;
                break;
            case BLANK:
            default:
                color = WHITE_COLOR;
                break;
        }
        return color;
    }
}
