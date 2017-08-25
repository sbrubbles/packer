package com.mobiquityinc.packer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.Objects;

/**
 * Object representation of each of the items that can be put in a {@link Pack}.
 */
public class Item extends Parameterized {
    private final int index;

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
        return o != null && getClass() == o.getClass() && index == ((Item) o).index;
    }

    @Override
    public int hashCode() {
        return index;
    }

    @Override
    public String toString() {
        return String.format("Item{index=%d, weight=%s, cost=%s}", index, getWeight(), getCost());
    }
}
