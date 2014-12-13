package org.doxu.iota.ui;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import org.doxu.iota.Card;

/**
 *
 * @author rafael
 */
public class CardRenderer {

    public static final int CARD_WIDTH = 24;

    public static final int INSET = 2;

    private static final Color OUTLINE_COLOR = new Color(0, 0, 0);
    private static final Color CARD_COLOR = new Color(255, 255, 255);
    private static final Color BLUE_COLOR = new Color(150, 200, 255);
    private static final Color GREEN_COLOR = new Color(144, 209, 64);
    private static final Color RED_COLOR = new Color(255, 100, 100);
    private static final Color YELLOW_COLOR = new Color(243, 247, 12);
    private static final Color WHITE_COLOR = new Color(255, 255, 255);

    public static void draw(Graphics2D g, Card card, int x, int y) {
        drawBackground(g, x, y);
        drawShape(g, card, x, y);
        drawDots(g, card, x, y);
    }

    private static void drawBackground(Graphics2D g, int x, int y) {
        g.setColor(CARD_COLOR);
        g.fillRect(x + INSET, y + INSET, CARD_WIDTH - INSET * 2, CARD_WIDTH - INSET * 2);
        g.setColor(OUTLINE_COLOR);
        g.drawRect(x + INSET, y + INSET, CARD_WIDTH - INSET * 2, CARD_WIDTH - INSET * 2);
    }

    private static void drawDots(Graphics2D g, Card card, int x, int y) {
        g.setColor(new Color(0, 0, 0));
        int dotWidth = 3;
        int offset = 2;
        switch (card.getCount()) {
            case ONE:
                g.fillRect(x + CARD_WIDTH / 2 - dotWidth / 2, y + CARD_WIDTH / 2 - dotWidth / 2, dotWidth, dotWidth);
                break;
            case TWO:
                g.fillRect(x + CARD_WIDTH / 2 - dotWidth / 2 - offset, y + CARD_WIDTH / 2 - dotWidth / 2, dotWidth, dotWidth);
                g.fillRect(x + CARD_WIDTH / 2 - dotWidth / 2 + offset, y + CARD_WIDTH / 2 - dotWidth / 2, dotWidth, dotWidth);
                break;
            case THREE:
                g.fillRect(x + CARD_WIDTH / 2 - dotWidth / 2, y + CARD_WIDTH / 2 - dotWidth / 2 - offset, dotWidth, dotWidth);
                g.fillRect(x + CARD_WIDTH / 2 - dotWidth / 2 - offset, y + CARD_WIDTH / 2 - dotWidth / 2 + offset, dotWidth, dotWidth);
                g.fillRect(x + CARD_WIDTH / 2 - dotWidth / 2 + offset, y + CARD_WIDTH / 2 - dotWidth / 2 + offset, dotWidth, dotWidth);
                break;
            case FOUR:
                g.fillRect(x + CARD_WIDTH / 2 - dotWidth / 2 - offset, y + CARD_WIDTH / 2 - dotWidth / 2 - offset, dotWidth, dotWidth);
                g.fillRect(x + CARD_WIDTH / 2 - dotWidth / 2 + offset, y + CARD_WIDTH / 2 - dotWidth / 2 - offset, dotWidth, dotWidth);
                g.fillRect(x + CARD_WIDTH / 2 - dotWidth / 2 - offset, y + CARD_WIDTH / 2 - dotWidth / 2 + offset, dotWidth, dotWidth);
                g.fillRect(x + CARD_WIDTH / 2 - dotWidth / 2 + offset, y + CARD_WIDTH / 2 - dotWidth / 2 + offset, dotWidth, dotWidth);
                break;
            case BLANK:
            default:
                break;
        }
    }

    private static void drawShape(Graphics2D g, Card card, int x, int y) {
        Color fillColor = getColor(card);
        switch (card.getShape()) {
            case CIRCLE:
                drawCircle(g, fillColor, x, y);
                break;
            case SQUARE:
                drawSquare(g, fillColor, x, y);
                break;
            case CROSS:
                drawCross(g, fillColor, x, y);
                break;
            case TRIANGLE:
                drawTriangle(g, fillColor, x, y);
                break;
            case BLANK:
            default:
                break;
        }
    }

    private static void drawCircle(Graphics2D g, Color fillColor, int x, int y) {
        int circleInset = 4;
        g.setColor(fillColor);
        g.fillOval(x + circleInset, y + circleInset, CARD_WIDTH - circleInset * 2, CARD_WIDTH - circleInset * 2);
        g.setColor(OUTLINE_COLOR);
        g.drawOval(x + circleInset, y + circleInset, CARD_WIDTH - circleInset * 2, CARD_WIDTH - circleInset * 2);
    }

    private static void drawSquare(Graphics2D g, Color fillColor, int x, int y) {
        int squareInset = 6;
        g.setColor(fillColor);
        g.fillRect(x + squareInset, y + squareInset, CARD_WIDTH - squareInset * 2, CARD_WIDTH - squareInset * 2);
        g.setColor(OUTLINE_COLOR);
        g.drawRect(x + squareInset, y + squareInset, CARD_WIDTH - squareInset * 2, CARD_WIDTH - squareInset * 2);
    }

    private static void drawCross(Graphics2D g, Color fillColor, int x, int y) {
        int crossWidth = 8;
        GeneralPath cross = new GeneralPath();
        // top
        cross.moveTo(x + CARD_WIDTH / 2 - crossWidth / 2, y + INSET);
        cross.lineTo(x + CARD_WIDTH / 2 + crossWidth / 2, y + INSET);
        // right top inside
        cross.lineTo(x + CARD_WIDTH / 2 + crossWidth / 2, y + CARD_WIDTH / 2 - crossWidth / 2);
        // right
        cross.lineTo(x + CARD_WIDTH - INSET, y + CARD_WIDTH / 2 - crossWidth / 2);
        cross.lineTo(x + CARD_WIDTH - INSET, y + CARD_WIDTH / 2 + crossWidth / 2);
        // right bottom inside
        cross.lineTo(x + CARD_WIDTH / 2 + crossWidth / 2, y + CARD_WIDTH / 2 + crossWidth / 2);
        // bottom
        cross.lineTo(x + CARD_WIDTH / 2 + crossWidth / 2, y + CARD_WIDTH - INSET);
        cross.lineTo(x + CARD_WIDTH / 2 - crossWidth / 2, y + CARD_WIDTH - INSET);
        // left bottom inside
        cross.lineTo(x + CARD_WIDTH / 2 - crossWidth / 2, y + CARD_WIDTH / 2 + crossWidth / 2);
        // left
        cross.lineTo(x + INSET, y + CARD_WIDTH / 2 + crossWidth / 2);
        cross.lineTo(x + INSET, y + CARD_WIDTH / 2 - crossWidth / 2);
        // left top inside
        cross.lineTo(x + CARD_WIDTH / 2 - crossWidth / 2, y + CARD_WIDTH / 2 - crossWidth / 2);
        cross.closePath();

        g.setColor(fillColor);
        g.fill(cross);
        g.setColor(OUTLINE_COLOR);
        g.draw(cross);
    }

    private static void drawTriangle(Graphics2D g, Color fillColor, int x, int y) {
        int bottomOffset = 2;
        GeneralPath tri = new GeneralPath();
        tri.moveTo(x + CARD_WIDTH / 2, y + INSET);
        tri.lineTo(x + INSET, y + CARD_WIDTH - INSET - bottomOffset);
        tri.lineTo(x + CARD_WIDTH - INSET, y + CARD_WIDTH - INSET - bottomOffset);
        tri.closePath();

        g.setColor(fillColor);
        g.fill(tri);
        g.setColor(OUTLINE_COLOR);
        g.draw(tri);
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
