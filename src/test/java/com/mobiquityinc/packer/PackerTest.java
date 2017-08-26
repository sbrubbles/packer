package com.mobiquityinc.packer;

import com.mobiquityinc.PackMocker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.fail;

/**
 * Test cases for validating the {@link Packer} class.
 *
 * @see Packer
 */
@RunWith(MockitoJUnitRunner.class)
public class PackerTest {

    /**
     * Tests if the application handles all its exceptions internally.
     *
     * @see Packer#main(String[])
     */
    @Test
    public void testMainValidInput() {
        try {
            Packer.main(new String[]{PackMocker.VALID_INPUT});
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Tests if the application handles all its exceptions internally.
     *
     * @see Packer#main(String[])
     */
    @Test
    public void testMainInvalidInput() {
        try {
            Packer.main(new String[]{PackMocker.INVALID_INPUT});
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /**
     * Tests if the application handles all its exceptions internally.
     *
     * @see Packer#main(String[])
     */
    @Test
    public void testMainNoArgs() {
        try {
            Packer.main(new String[]{});
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }
}
