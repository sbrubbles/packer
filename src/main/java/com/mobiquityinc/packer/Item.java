package com.mobiquityinc.packer;

import java.math.BigDecimal;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() || !super.equals(o)) return false;
        return index == ((Item) o).index;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), index);
    }

    @Override
    public String toString() {
        return String.format("Item{index=%d, weight=%s, cost=%s}", index, getWeight(), getCost());
    }
}
