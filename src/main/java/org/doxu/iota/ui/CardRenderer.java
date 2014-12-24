package org.doxu.iota.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import org.doxu.iota.Card;

public class CardRenderer {

    public static final int CARD_WIDTH = 24;

    public static final int INSET = 2;

    private static final Color OUTLINE_COLOR = new Color(0, 0, 0);
    private static final Color DOT_COLOR = new Color(0, 0, 0);
    private static final Color CARD_COLOR = new Color(255, 255, 255);
    private static final Color BLUE_COLOR = new Color(150, 200, 255);
    private static final Color GREEN_COLOR = new Color(144, 209, 64);
    private static final Color RED_COLOR = new Color(255, 100, 100);
    private static final Color YELLOW_COLOR = new Color(243, 247, 12);
    private static final Color WHITE_COLOR = new Color(255, 255, 255);

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
        int dotWidth = 3;
        int offset = 2;
        switch (card.getCount()) {
            case ONE:
                g.fillRect(CARD_WIDTH / 2 - dotWidth / 2, CARD_WIDTH / 2 - dotWidth / 2, dotWidth, dotWidth);
                break;
            case TWO:
                g.fillRect(CARD_WIDTH / 2 - dotWidth / 2 - offset, CARD_WIDTH / 2 - dotWidth / 2, dotWidth, dotWidth);
                g.fillRect(CARD_WIDTH / 2 - dotWidth / 2 + offset, CARD_WIDTH / 2 - dotWidth / 2, dotWidth, dotWidth);
                break;
            case THREE:
                g.fillRect(CARD_WIDTH / 2 - dotWidth / 2, CARD_WIDTH / 2 - dotWidth / 2 - offset, dotWidth, dotWidth);
                g.fillRect(CARD_WIDTH / 2 - dotWidth / 2 - offset, CARD_WIDTH / 2 - dotWidth / 2 + offset, dotWidth, dotWidth);
                g.fillRect(CARD_WIDTH / 2 - dotWidth / 2 + offset, CARD_WIDTH / 2 - dotWidth / 2 + offset, dotWidth, dotWidth);
                break;
            case FOUR:
                g.fillRect(CARD_WIDTH / 2 - dotWidth / 2 - offset, CARD_WIDTH / 2 - dotWidth / 2 - offset, dotWidth, dotWidth);
                g.fillRect(CARD_WIDTH / 2 - dotWidth / 2 + offset, CARD_WIDTH / 2 - dotWidth / 2 - offset, dotWidth, dotWidth);
                g.fillRect(CARD_WIDTH / 2 - dotWidth / 2 - offset, CARD_WIDTH / 2 - dotWidth / 2 + offset, dotWidth, dotWidth);
                g.fillRect(CARD_WIDTH / 2 - dotWidth / 2 + offset, CARD_WIDTH / 2 - dotWidth / 2 + offset, dotWidth, dotWidth);
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
        int circleInset = 4;
        g.setColor(fillColor);
        g.fillOval(circleInset, circleInset, CARD_WIDTH - circleInset * 2, CARD_WIDTH - circleInset * 2);
        g.setColor(OUTLINE_COLOR);
        g.drawOval(circleInset, circleInset, CARD_WIDTH - circleInset * 2, CARD_WIDTH - circleInset * 2);
    }

    private static void drawSquare(Graphics g, Color fillColor) {
        int squareInset = 6;
        g.setColor(fillColor);
        g.fillRect(squareInset, squareInset, CARD_WIDTH - squareInset * 2, CARD_WIDTH - squareInset * 2);
        g.setColor(OUTLINE_COLOR);
        g.drawRect(squareInset, squareInset, CARD_WIDTH - squareInset * 2, CARD_WIDTH - squareInset * 2);
    }

    private static void drawCross(Graphics2D g, Color fillColor) {
        int crossWidth = 8;
        GeneralPath cross = new GeneralPath();
        // top
        cross.moveTo(CARD_WIDTH / 2 - crossWidth / 2, INSET);
        cross.lineTo(CARD_WIDTH / 2 + crossWidth / 2, INSET);
        // right top inside
        cross.lineTo(CARD_WIDTH / 2 + crossWidth / 2, CARD_WIDTH / 2 - crossWidth / 2);
        // right
        cross.lineTo(CARD_WIDTH - INSET, CARD_WIDTH / 2 - crossWidth / 2);
        cross.lineTo(CARD_WIDTH - INSET, CARD_WIDTH / 2 + crossWidth / 2);
        // right bottom inside
        cross.lineTo(CARD_WIDTH / 2 + crossWidth / 2, CARD_WIDTH / 2 + crossWidth / 2);
        // bottom
        cross.lineTo(CARD_WIDTH / 2 + crossWidth / 2, CARD_WIDTH - INSET);
        cross.lineTo(CARD_WIDTH / 2 - crossWidth / 2, CARD_WIDTH - INSET);
        // left bottom inside
        cross.lineTo(CARD_WIDTH / 2 - crossWidth / 2, CARD_WIDTH / 2 + crossWidth / 2);
        // left
        cross.lineTo(INSET, CARD_WIDTH / 2 + crossWidth / 2);
        cross.lineTo(INSET, CARD_WIDTH / 2 - crossWidth / 2);
        // left top inside
        cross.lineTo(CARD_WIDTH / 2 - crossWidth / 2, CARD_WIDTH / 2 - crossWidth / 2);
        cross.closePath();

        g.setColor(fillColor);
        g.fill(cross);
        g.setColor(OUTLINE_COLOR);
        g.draw(cross);
    }

    private static void drawTriangle(Graphics2D g, Color fillColor) {
        int bottomOffset = 2;
        GeneralPath tri = new GeneralPath();
        tri.moveTo(CARD_WIDTH / 2, INSET);
        tri.lineTo(INSET, CARD_WIDTH - INSET - bottomOffset);
        tri.lineTo(CARD_WIDTH - INSET, CARD_WIDTH - INSET - bottomOffset);
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
