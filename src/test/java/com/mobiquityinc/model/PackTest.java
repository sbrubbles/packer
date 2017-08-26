package com.mobiquityinc.model;

import org.junit.Test;

import java.math.BigDecimal;

import static com.mobiquityinc.PackMocker.item;
import static com.mobiquityinc.PackMocker.pack;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

/**
 * Test cases for validating the {@link Pack} class.
 *
 * @see Pack
 */
public class PackTest {
    /**
     * Validate if the addItem method is correctly inserting the items and refreshing the cost and weight values
     *
     * @see Pack#addItem(Item)
     */
    @Test
    public void testAddItem() {
        Pack pack = pack("10", "0");
        Item item1 = item(1, "20", "20");
        Item item2 = item(0, "10", "10");

        pack.addItem(item1);

        assertThat(pack.getItems().size(), is(1));
        assertThat(pack.getWeight(), comparesEqualTo(BigDecimal.valueOf(30)));
        assertThat(pack.getCost(), comparesEqualTo(BigDecimal.valueOf(20)));

        pack.addItem(item2);

        assertThat(pack.getItems().size(), is(2));
        assertThat(pack.getWeight(), comparesEqualTo(BigDecimal.valueOf(40)));
        assertThat(pack.getCost(), comparesEqualTo(BigDecimal.valueOf(30)));
    }

    /**
     * Validates the equality check of the equals method
     *
     * @see Pack#equals(Object)
     */
    @Test
    public void testEquals() {
        Pack pack1 = pack("10", "0", item(1, "10", "20"));
        Pack pack2 = pack("10", "0", item(1, "10", "20"));

        assertThat(pack1.equals(pack2), is(true));
    }

    /**
     * Validates the behavior of the equals method when it receives a null argument
     *
     * @see Pack#equals(Object)
     */
    @Test
    public void testEqualsNull() {
        Pack pack1 = pack("10", "0", item(1, "10", "20"));
        Pack pack2 = null;

        assertThat(pack1.equals(pack2), is(false));
    }

    /**
     * Validates the behavior of the equals method when receiving a Pack with different weight
     *
     * @see Pack#equals(Object)
     */
    @Test
    public void testEqualsDifferentWeight() {
        Pack pack1 = pack("10", "0", item(1, "10", "20"));
        Pack pack2 = pack("20", "0", item(1, "10", "20"));

        assertThat(pack1.equals(pack2), is(false));
    }

    /**
     * Validates the behavior of the equals method when receiving a Pack with different cost
     *
     * @see Pack#equals(Object)
     */
    @Test
    public void testEqualsDifferentCost() {
        Pack pack1 = pack("10", "0", item(1, "10", "20"));
        Pack pack2 = pack("10", "1", item(1, "10", "20"));

        assertThat(pack1.equals(pack2), is(false));
    }

    /**
     * Validates the behavior of the equals method when receiving a Pack with different items
     *
     * @see Pack#equals(Object)
     */
    @Test
    public void testEqualsDifferentItems() {
        Pack pack1 = pack("10", "0", item(1, "10", "20"));
        Pack pack2 = pack("10", "0", item(2, "20", "30"));

        assertThat(pack1.equals(pack2), is(false));
    }

    /**
     * Validates the behavior of the equals method when receiving a Pack with different item count
     *
     * @see Pack#equals(Object)
     */
    @Test
    public void testEqualsDifferentItemCount() {
        Pack pack1 = pack("10", "0", item(1, "10", "20"));
        Pack pack2 = pack("10", "0", item(1, "10", "20"), item(1, "10", "20"));

        assertThat(pack1.equals(pack2), is(false));
    }

    /**
     * Validates the behavior of the equals method when receiving a Pack with the same values, but different decimal digit count
     *
     * @see Pack#equals(Object)
     */
    @Test
    public void testEqualsDecimals() {
        Pack pack1 = pack("10", "0", item(1, "10", "20"));
        Pack pack2 = pack("10.0", "0.0", item(1, "10.0", "20.0"));

        assertThat(pack1.equals(pack2), is(true));
    }

    /**
     * Validates the behavior of the equals method when receiving an object with different class
     *
     * @see Pack#equals(Object)
     */
    @Test
    public void testEqualsDifferentClass() {
        Pack pack = pack("10", "0", item(1, "10", "20"));
        Item item = item(1, "10", "20");

        assertThat(pack.equals(item), is(false));
    }

    /**
     * Validates if two equal Packs have the same hashcode
     *
     * @see Pack#hashCode()
     */
    @Test
    public void testHashCode() {
        Pack pack1 = pack("10", "0", item(1, "10", "20"));
        Pack pack2 = pack("10", "0", item(1, "10", "20"));

        assertThat(pack1.hashCode() == pack2.hashCode(), is(true));
    }

    /**
     * Validates that two Packs with different weights don't have the same hashcode
     *
     * @see Pack#hashCode()
     */
    @Test
    public void testHashCodeDifferentWeight() {
        Pack pack1 = pack("10", "0", item(1, "10", "20"));
        Pack pack2 = pack("20", "0", item(1, "10", "20"));

        assertThat(pack1.hashCode() == pack2.hashCode(), is(false));
    }

    /**
     * Validates that two Packs with different costs don't have the same hashcode
     *
     * @see Pack#hashCode()
     */
    @Test
    public void testHashCodeDifferentCost() {
        Pack pack1 = pack("10", "0", item(1, "10", "20"));
        Pack pack2 = pack("10", "1", item(1, "10", "20"));

        assertThat(pack1.hashCode() == pack2.hashCode(), is(false));
    }

    /**
     * Validates that two Packs with different items don't have the same hashcode
     *
     * @see Pack#hashCode()
     */
    @Test
    public void testHashCodeDifferentItems() {
        Pack pack1 = pack("10", "0", item(1, "10", "20"));
        Pack pack2 = pack("10", "0", item(2, "20", "30"));

        assertThat(pack1.hashCode() == pack2.hashCode(), is(false));
    }

    /**
     * Validates that a Pack with no items will have a String representation as a single dash
     *
     * @see Pack#toString()
     */
    @Test
    public void testToStringEmptyItems() {
        Pack pack = pack("10", "0");

        assertThat("-", equalTo(pack.toString()));
    }

    /**
     * Validates that a Pack will be represented as String by the list of its Item's indexes
     *
     * @see Pack#toString()
     */
    @Test
    public void testToStringSingleItem() {
        Pack pack = pack("10", "0", item(1, "10", "20"));

        assertThat("1", equalTo(pack.toString()));
    }

    /**
     * Validates that a Pack will be represented as String by the list of its Item's indexes
     *
     * @see Pack#toString()
     */
    @Test
    public void testToStringMultipleItem() {
        Pack pack = pack("10", "0",
                item(1, "10", "20"),
                item(3, "10", "20"),
                item(10, "10", "20"));

        assertThat("1,3,10", equalTo(pack.toString()));
    }

    /**
     * Validates that a Pack with the same weight and cost will be compared as equals
     *
     * @see Parameterized#compareTo(Parameterized)
     */
    @Test
    public void testCompareTo() {
        Pack pack1 = pack("10", "0", item(1, "10", "20"));
        Pack pack2 = pack("10", "0");

        assertThat(pack1.compareTo(pack2), is(0));
    }

    /**
     * Validates that the Pack with highest cost will be ordered first
     *
     * @see Parameterized#compareTo(Parameterized)
     */
    @Test
    public void testCompareToHighestCost() {
        Pack pack1 = pack("10", "1");
        Pack pack2 = pack("10", "0");

        assertThat(pack1.compareTo(pack2), lessThan(0));
        assertThat(pack2.compareTo(pack1), greaterThan(0));
    }

    /**
     * Validates that the Pack with lowest weight will be ordered first, if the costs happen to be equal
     *
     * @see Parameterized#compareTo(Parameterized)
     */
    @Test
    public void testCompareToLowestWeight() {
        Pack pack1 = pack("0", "0");
        Pack pack2 = pack("10", "0");

        assertThat(pack1.compareTo(pack2), lessThan(0));
        assertThat(pack2.compareTo(pack1), greaterThan(0));
    }
}
