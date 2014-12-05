package org.doxu.iota.attr;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rafael
 */
public class LotValidatorTest {

    /**
     * Test of validate3 method, of class LotValidator.
     */
    @Test
    public void testValidate3() {
        assertTrue(LotValidator.validate3(Shape.CIRCLE, Shape.CIRCLE, Shape.CIRCLE));
        assertTrue(LotValidator.validate3(Shape.CROSS, Shape.CROSS, Shape.CROSS));
        assertTrue(LotValidator.validate3(Shape.TRIANGLE, Shape.CIRCLE, Shape.CROSS));
        assertFalse(LotValidator.validate3(Shape.CIRCLE, Shape.CROSS, Shape.CIRCLE));
        assertFalse(LotValidator.validate3(Shape.CIRCLE, Shape.CROSS, Shape.CROSS));
    }

    /**
     * Test of validate4 method, of class LotValidator.
     */
    @Test
    public void testValidate4() {
        assertTrue(LotValidator.validate4(Shape.CIRCLE, Shape.CIRCLE, Shape.CIRCLE, Shape.CIRCLE));
        assertTrue(LotValidator.validate4(Shape.TRIANGLE, Shape.CIRCLE, Shape.CROSS, Shape.SQUARE));
        assertTrue(LotValidator.validate4(Shape.CROSS, Shape.CROSS, Shape.CROSS, Shape.CROSS));
        assertFalse(LotValidator.validate4(Shape.CIRCLE, Shape.CROSS, Shape.CIRCLE, Shape.CROSS));
        assertFalse(LotValidator.validate4(Shape.CIRCLE, Shape.CROSS, Shape.CIRCLE, Shape.CIRCLE));
    }

}
