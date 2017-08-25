package com.mobiquityinc.packer;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Object representation of each of the packs that can be read from the input file or created to contain the items picked.
 */
public class Pack extends Parameterized {
    private final List<Item> items = new ArrayList<>();

    public Pack() {
        super(BigDecimal.ZERO, BigDecimal.ZERO);
    }

    /**
     * Adds an item to the package, updating it's total cost and total weight
     *
     * @param item the item to be added to the package
     */
    public void addItem(Item item) {
        items.add(item);
        setCost(getCost().add(item.getCost()));
        setWeight(getWeight().add(item.getWeight()));
    }

    /**
     * The list of items contained by the pack, being either result from the parsed input file or already distributed items representing
     * the solution. This property is immutable.
     *
     * @return the list of items for this pack
     */
    public List<Item> getItems() {
        return items;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() || !super.equals(o)) return false;
        return Objects.equals(items, ((Pack) o).items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), items);
    }

    @Override
    public String toString() {
        return items.isEmpty() ? "-" : String.join(",",
                items.stream()
                        .map(i -> String.valueOf(i.getIndex()))
                        .collect(Collectors.toList()));
    }
}
