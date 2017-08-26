package com.mobiquityinc.model;

import org.junit.Test;

import static com.mobiquityinc.PackMocker.item;
import static com.mobiquityinc.PackMocker.pack;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

/**
 * Test cases for validating the {@link Item} class.
 *
 * @see Item
 */
public class ItemTest {
    /**
     * Validates the equality check of the equals method
     *
     * @see Item#equals(Object)
     */
    @Test
    public void testEquals() {
        Item item1 = item(1, "10", "20");
        Item item2 = item(1, "10", "20");

        assertThat(item1.equals(item2), is(true));
    }

    /**
     * Validates the behavior of the equals method when it receives a null argument
     *
     * @see Item#equals(Object)
     */
    @Test
    public void testEqualsNull() {
        Item item1 = item(1, "10", "20");
        Item item2 = null;

        assertThat(item1.equals(item2), is(false));
    }

    /**
     * Validates the behavior of the equals method when receiving an Item with different index
     *
     * @see Item#equals(Object)
     */
    @Test
    public void testEqualsDifferentIndex() {
        Item item1 = item(1, "10", "20");
        Item item2 = item(2, "10", "20");

        assertThat(item1.equals(item2), is(false));
    }

    /**
     * Validates the behavior of the equals method when receiving an Item with different weight
     *
     * @see Item#equals(Object)
     */
    @Test
    public void testEqualsDifferentWeight() {
        Item item1 = item(1, "10", "20");
        Item item2 = item(1, "20", "20");

        assertThat(item1.equals(item2), is(false));
    }

    /**
     * Validates the behavior of the equals method when receiving an Item with different cost
     *
     * @see Item#equals(Object)
     */
    @Test
    public void testEqualsDifferentCost() {
        Item item1 = item(1, "10", "20");
        Item item2 = item(1, "10", "10");

        assertThat(item1.equals(item2), is(false));
    }

    /**
     * Validates the behavior of the equals method when receiving an Item with the same values, but different decimal digit count
     *
     * @see Item#equals(Object)
     */
    @Test
    public void testEqualsDecimals() {
        Item item1 = item(1, "10.0", "20.0");
        Item item2 = item(1, "10", "20");

        assertThat(item1.equals(item2), is(true));
    }

    /**
     * Validates the behavior of the equals method when receiving an object with different class
     *
     * @see Item#equals(Object)
     */
    @Test
    public void testEqualsDifferentClass() {
        Item item = item(1, "10", "20");
        Pack pack = pack("10", "20");

        assertThat(item.equals(pack), is(false));
    }

    /**
     * Validates if two equal Items have the same hashcode
     *
     * @see Item#hashCode()
     */
    @Test
    public void testHashCode() {
        Item item1 = item(1, "10", "20");
        Item item2 = item(1, "10", "20");

        assertThat(item1.hashCode() == item2.hashCode(), is(true));
    }

    /**
     * Validates that two Items with different indexes don't have the same hashcode
     *
     * @see Item#hashCode()
     */
    @Test
    public void testHashCodeDifferentIndex() {
        Item item1 = item(1, "10", "20");
        Item item2 = item(2, "10", "20");

        assertThat(item1.hashCode() == item2.hashCode(), is(false));
    }

    /**
     * Validates that two Items with different weights don't have the same hashcode
     *
     * @see Item#hashCode()
     */
    @Test
    public void testHashCodeDifferentWeight() {
        Item item1 = item(1, "10", "20");
        Item item2 = item(1, "20", "20");

        assertThat(item1.hashCode() == item2.hashCode(), is(false));
    }

    /**
     * Validates that two Items with different costs don't have the same hashcode
     *
     * @see Item#hashCode()
     */
    @Test
    public void testHashCodeDifferentCost() {
        Item item1 = item(1, "10", "20");
        Item item2 = item(1, "10", "10");

        assertThat(item1.hashCode() == item2.hashCode(), is(false));
    }

    /**
     * Validates that two Items with the same weight and cost will be compared as ewquals
     *
     * @see Parameterized#compareTo(Parameterized)
     */
    @Test
    public void testCompareTo() {
        Item item1 = item(1, "10", "20");
        Item item2 = item(2, "10", "20");

        assertThat(item1.compareTo(item2), is(0));
    }

    /**
     * Validates that the Item with highest cost will be ordered first
     *
     * @see Parameterized#compareTo(Parameterized)
     */
    @Test
    public void testCompareToHighestCost() {
        Item item1 = item(1, "10", "20");
        Item item2 = item(2, "10", "10");

        assertThat(item1.compareTo(item2), lessThan(0));
        assertThat(item2.compareTo(item1), greaterThan(0));
    }

    /**
     * Validates that the Item with lowest weight will be ordered first, if the costs happen to be equal
     *
     * @see Parameterized#compareTo(Parameterized)
     */
    @Test
    public void testCompareToLowestWeight() {
        Item item1 = item(1, "10", "20");
        Item item2 = item(2, "20", "20");

        assertThat(item1.compareTo(item2), lessThan(0));
        assertThat(item2.compareTo(item1), greaterThan(0));
    }
}
