package com.mobiquityinc;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.packer.APIPreconditions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class APIPreconditionsTest {
    @Test
    public void testCheckConditionTrue() throws Exception {
        try {
            APIPreconditions.checkCondition(true, "message");
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test(expected = APIException.class)
    public void testCheckConditionFalse() throws Exception {
        APIPreconditions.checkCondition(false, "message");
    }
}
