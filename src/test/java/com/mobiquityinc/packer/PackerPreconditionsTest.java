package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PackerPreconditionsTest {
    @Test
    public void testCheckConditionTrue() throws Exception {
        try {
            PackerPreconditions.checkCondition(true, "message");
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(expected = APIException.class)
    public void testCheckConditionFalse() throws Exception {
        PackerPreconditions.checkCondition(false, "message");
    }
}
