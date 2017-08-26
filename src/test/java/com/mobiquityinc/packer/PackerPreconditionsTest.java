package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Test cases for validating the {@link PackerPreconditions} class.
 *
 * @see PackerPreconditions
 */
@RunWith(MockitoJUnitRunner.class)
public class PackerPreconditionsTest {
    /**
     * Tests the behavior of the check when the condition is true.
     *
     * @see PackerPreconditions#checkCondition(boolean, String, Object...)
     */
    @Test
    public void testCheckConditionTrue() {
        try {
            PackerPreconditions.checkCondition(true, "message");
        } catch (Exception e) {
            Assert.fail();
        }
    }

    /**
     * Tests the behavior of the check when the condition is false.
     *
     * @see PackerPreconditions#checkCondition(boolean, String, Object...)
     */
    @Test(expected = APIException.class)
    public void testCheckConditionFalse() {
        PackerPreconditions.checkCondition(false, "message");
    }
}
