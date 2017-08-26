package com.mobiquityinc.model;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Object representation of each of the items that can be put in a {@link Pack}.
 */
public class Item extends Parameterized {
    private final int index;

    /**
     * Instantiates a new Item, setting the {@code index}, {@code weight} and {@code cost}.
     *
     * @param index  the index of the new instance
     * @param weight the weight of the new instance
     * @param cost   the cost of the new instance
     */
    public Item(int index, BigDecimal weight, BigDecimal cost) {
        super(weight, cost);
        this.index = index;
    }

    /**
     * The index of the item in the original pack, parsed from the input file. This property is immutable.
     *
     * @return the item's index
     */
    public int getIndex() {
        return index;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && index == ((Item) o).index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), index);
    }
}
