package com.mobiquityinc.packer;

import com.mobiquityinc.model.Item;
import com.mobiquityinc.model.Pack;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.mobiquityinc.packer.PackerPreconditions.checkCondition;

/**
 * Is responsible for parsing a file containing a String representation of the packs and determine what is the best subset of items from
 * a group that fit in a pack, summing the highest value without exceeding the pack's maximum weight.
 */
public class PackProcessor {
    private static final int MAX_ITEMS_PER_LIST = 15;
    private static final BigDecimal MAX_PACKAGE_WEIGHT = BigDecimal.valueOf(100);
    private static final BigDecimal MAX_ITEM_WEIGHT = BigDecimal.valueOf(100);
    private static final BigDecimal MAX_ITEM_COST = BigDecimal.valueOf(100);

    private PackFileReader fileReader = new PackFileReader();

    /**
     * This method accepts a file path, that will have each pf its lines parsed into a {@link Pack}, containing items that can be chosen
     * to fit the result package.
     * <p>
     * The result package is a new Pack instance, containing the item that sum the highest cost, without exceeding the maximum weight of
     * the pack. In case of more than one combination having the same total cost, it will favor the one with the lowest weight.
     * <p>
     * The return of this method will be a String, containing the index of the chosen items, split by commas.
     *
     * @param filePath the path to be parsed into packs
     * @return a String representation of the indexes of the items achieving the highest value, without exceeding the maximum weight
     * @see PackFileReader#parseLine(String)
     */
    public String processFile(String filePath) {
        List<Pack> packs = fileReader.readFile(filePath);

        return String.join("\n",
                packs.stream()
                        .peek(this::validatePack)
                        .map(this::pickItems)
                        .map(Pack::toString)
                        .collect(Collectors.toList()));
    }

    /**
     * This method will generate all the different possible combinations of items that don't exceed the maximum weight and return the
     * best one (highest value, without exceeding the maximum weight).
     * <p>
     * It is a slightly modified implementation of a <a href='https://en.wikipedia.org/wiki/Power_set'>power set</a>, tweaked
     * to be more performatic by eliminating the combinations that don't fit the weight constraints.
     * <p>
     * Steps:
     * <ul>
     * <li>1. Sorts all the items that can be chosen, by highest cost/lowest weight;</li>
     * <li>2. Outer loop, for controlling the items that were already matched with the others;</li>
     * <li>3. Inner loop, for getting the item that will be actually matched against the others;</li>
     * <li>4. Determine what item is the one to be matched with the remaining ones;</li>
     * <li>5. If the item being matched exceeds the weight by itself, there's no point in checking its combinations;</li>
     * <li>6. The actual matcher loop, that will try the remaining possible combinations for the item;</li>
     * <li>7. The check to determine the item from the loop is not the same being matched and if its addition won't exceed the maximum
     * weight;</li>
     * <li>8. Store the most recently created pack, if it is better than the other generated by previous iterations.</li>
     * </ul>
     *
     * @param allItems a pack contained all items that can be chosen
     * @return the best solution for the given items
     */
    private Pack pickItems(Pack allItems) {
        Collections.sort(allItems.getItems()); // step 1
        Pack best = new Pack();
        int size = allItems.getItems().size();

        for (int i = 0; i < size; i++) { // step 2
            for (int j = 0; j < size; j++) { // step 3
                Pack current = new Pack();
                Item matching = allItems.getItems().get(j); // step 4
                if (!exceedsWeight(allItems.getWeight(), current, matching)) { // step 5
                    current.addItem(matching);
                    for (Item item : allItems.getItems().subList(i, size)) { // step 6
                        if (!item.equals(matching) && !exceedsWeight(allItems.getWeight(), current, item)) { // step 7
                            current.addItem(item);
                        }
                    }
                }
                if (current.compareTo(best) < 0) { // step 8
                    best = current;
                }
            }
        }
        best.getItems().sort(Comparator.comparing(Item::getIndex));
        return best;
    }

    /**
     * Checks if the given {@code item} will not exceed {@code maxWeight} if added to the {@code pack}.
     *
     * @param maxWeight the maximum allowed weight for the pack
     * @param pack      the pack where the item should be added
     * @param item      the item to be added
     * @return {@code true} if the sum of the weights of the pack and item exceeds the maximum allowed weight; {@code false} otherwise.
     */
    private boolean exceedsWeight(BigDecimal maxWeight, Pack pack, Item item) {
        return pack.getWeight().add(item.getWeight()).compareTo(maxWeight) > 0;
    }

    /**
     * Validates a pack, according to the following rules:
     * <ul>
     * <li>The pack weight should not exceed {@link #MAX_PACKAGE_WEIGHT}</li>
     * <li>The item count inside the package should not exceed {@link #MAX_ITEMS_PER_LIST}</li>
     * <li>No item inside the pack should weight more than {@link #MAX_ITEM_WEIGHT}</li>
     * <li>No item inside the pack should cost more than {@link #MAX_ITEM_COST}</li>
     * </ul>
     * <p>
     * If any of these rules is violated, the method will throw an {@link com.mobiquityinc.exception.APIException} with an appropriate
     * error message.
     *
     * @param pack the pack to be validated
     * @throws com.mobiquityinc.exception.APIException if any of the validation constraints is not met
     */
    private void validatePack(Pack pack) {
        checkCondition(pack.getWeight().compareTo(MAX_PACKAGE_WEIGHT) <= 0,
                "The package weight (%s) is larger than the maximum allowed (%s)", pack.getWeight(), MAX_PACKAGE_WEIGHT);
        checkCondition(pack.getItems().size() <= MAX_ITEMS_PER_LIST,
                "The amount of items in the list (%s) shouldn't exceed %s", pack.getItems().size(), MAX_ITEMS_PER_LIST);
        for (Item item : pack.getItems()) {
            checkCondition(item.getWeight().compareTo(MAX_ITEM_WEIGHT) <= 0,
                    "The item weight (%s) is larger than the maximum allowed (%s)", item.getWeight(), MAX_ITEM_WEIGHT);
            checkCondition(item.getCost().compareTo(MAX_ITEM_COST) <= 0,
                    "The item cost (%s) is larger then the maximum allowed (%s)", item.getCost(), MAX_ITEM_COST);
        }
    }
}
