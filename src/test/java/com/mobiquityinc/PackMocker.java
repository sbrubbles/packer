package com.mobiquityinc;

import com.mobiquityinc.model.Item;
import com.mobiquityinc.model.Pack;

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * A helper class to provide mocks to be used in unit tests
 */
public class PackMocker {
    /**
     * The path for a valid input file
     */
    public static final String VALID_INPUT = "src/test/resources/valid.txt";
    /**
     * The path for an invalid input file
     */
    public static final String INVALID_INPUT = "src/test/resources/invalid.txt";

    /**
     * Generates a new Pack, with the given parameters
     *
     * @param weight the weight of the Pack
     * @param cost   the cost of the Pack
     * @param items  the items of the Pack
     * @return a new instance of Pack
     */
    public static Pack pack(String weight, String cost, Item... items) {
        Pack pack = new Pack();
        pack.setWeight(new BigDecimal(weight));
        pack.setCost(new BigDecimal(cost));
        pack.getItems().addAll(Arrays.asList(items));
        return pack;
    }

    /**
     * Generates a new Item, with the given parameters
     *
     * @param index  the index of the Item
     * @param weight the weight of the Item
     * @param cost   the cost of the Item
     * @return a new instance of Item
     */
    public static Item item(int index, String weight, String cost) {
        return new Item(index, new BigDecimal(weight), new BigDecimal(cost));
    }
}
