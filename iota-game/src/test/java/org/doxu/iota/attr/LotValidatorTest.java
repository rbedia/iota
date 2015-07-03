package org.doxu.iota.attr;

import java.util.Arrays;
import java.util.List;
import org.doxu.iota.Card;
import org.doxu.iota.IllegalLaydownException;
import org.junit.Test;
import static org.junit.Assert.*;

public class LotValidatorTest {

    @Test(expected = IllegalLaydownException.class)
    public void testValidateBad1() throws IllegalLaydownException {
        LotValidator.validate(createLot("RS1", "GS2", "BS2"));
    }

    @Test(expected = IllegalLaydownException.class)
    public void testValidateBad2() throws IllegalLaydownException {
        LotValidator.validate(createLot("RS2", "GS2", "BO2"));
    }

    @Test(expected = IllegalLaydownException.class)
    public void testValidateBad3() throws IllegalLaydownException {
        LotValidator.validate(createLot("RS1", "GS2", "GS3"));
    }

    @Test(expected = IllegalLaydownException.class)
    public void testValidateBad4() throws IllegalLaydownException {
        LotValidator.validate(createLot("RS1", "GS2", "BS3", "YS3"));
    }

    @Test(expected = IllegalLaydownException.class)
    public void testValidateBad5() throws IllegalLaydownException {
        LotValidator.validate(createLot("RS1", "GS2", "BS3", "Y+4"));
    }

    @Test(expected = IllegalLaydownException.class)
    public void testValidateBad6() throws IllegalLaydownException {
        LotValidator.validate(createLot("RS1", "RS2", "RS3", "YS4"));
    }

    @Test
    public void testValidateGood1() throws IllegalLaydownException {
        LotValidator.validate(createLot("YT1", "YT2", "YT3"));
    }

    @Test
    public void testValidateGood2() throws IllegalLaydownException {
        LotValidator.validate(createLot("YS2", "Y+2", "YT2"));
    }

    @Test
    public void testValidateGood3() throws IllegalLaydownException {
        LotValidator.validate(createLot("Y+2", "B+2", "G+2"));
    }

    @Test
    public void testValidateGood4() throws IllegalLaydownException {
        LotValidator.validate(createLot("YT1", "B+2", "GS4"));
    }

    @Test
    public void testValidateGood5() throws IllegalLaydownException {
        LotValidator.validate(createLot("YT1", "YT2", "YT3", "YT4"));
    }

    @Test
    public void testValidateGood6() throws IllegalLaydownException {
        LotValidator.validate(createLot("YS2", "Y+2", "YT2", "YO2"));
    }

    @Test
    public void testValidateGood7() throws IllegalLaydownException {
        LotValidator.validate(createLot("Y+2", "B+2", "G+2", "R+2"));
    }

    @Test
    public void testValidateGood8() throws IllegalLaydownException {
        LotValidator.validate(createLot("YT1", "B+2", "GS4", "RO3"));
    }

    private List<Card> createLot(String card1, String card2, String card3) {
        List<Card> cards = Arrays.asList(Card.create(card1), Card.create(card2), Card.create(card3));
        return cards;
    }

    private List<Card> createLot(String card1, String card2, String card3, String card4) {
        List<Card> cards = Arrays.asList(Card.create(card1), Card.create(card2), Card.create(card3), Card.create(card4));
        return cards;
    }

    @Test
    public void testValidate3() {
        assertTrue(LotValidator.validate3(Shape.CIRCLE, Shape.CIRCLE, Shape.CIRCLE));
        assertTrue(LotValidator.validate3(Shape.CROSS, Shape.CROSS, Shape.CROSS));
        assertTrue(LotValidator.validate3(Shape.TRIANGLE, Shape.CIRCLE, Shape.CROSS));
        assertFalse(LotValidator.validate3(Shape.CIRCLE, Shape.CROSS, Shape.CIRCLE));
        assertFalse(LotValidator.validate3(Shape.CIRCLE, Shape.CROSS, Shape.CROSS));
    }

    @Test
    public void testValidate4() {
        assertTrue(LotValidator.validate4(Shape.CIRCLE, Shape.CIRCLE, Shape.CIRCLE, Shape.CIRCLE));
        assertTrue(LotValidator.validate4(Shape.TRIANGLE, Shape.CIRCLE, Shape.CROSS, Shape.SQUARE));
        assertTrue(LotValidator.validate4(Shape.CROSS, Shape.CROSS, Shape.CROSS, Shape.CROSS));
        assertFalse(LotValidator.validate4(Shape.CIRCLE, Shape.CROSS, Shape.CIRCLE, Shape.CROSS));
        assertFalse(LotValidator.validate4(Shape.CIRCLE, Shape.CROSS, Shape.CIRCLE, Shape.CIRCLE));
    }

}
