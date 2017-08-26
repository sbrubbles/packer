package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.Item;
import com.mobiquityinc.model.Pack;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.mobiquityinc.packer.PackerPreconditions.checkCondition;

/**
 * Class responsible for reading a file containing the string representation of packs of items and parsing them into a list of {@link Pack}.
 */
public class PackFileReader {
    /**
     * Reads a file from a given path and return the correspondent list of Pack represented by the file's contents.
     * <p>
     * Each line of the file should represent one pack, and must follow the pattern:
     * <p>
     * {@code [maximumWeight] : ([itemIndex],[itemWeight],[itemCost]) ([itemIndex],[itemWeight],[itemCost])...}
     *
     * @param filePath the path to the file containing the packs' representations
     * @return a list of Pack
     */
    public List<Pack> readFile(String filePath) {
        File file = new File(filePath);
        try {
            return Files.readAllLines(file.toPath())
                    .stream()
                    .map(this::parseLine)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new APIException(e);
        }
    }

    /**
     * Will parse a line containing a representation of a pack into an actual {@link Pack}.
     *
     * @param line the line to be parsed
     * @return the Pack parsed from the line
     * @see #readFile(String)
     */
    private Pack parseLine(String line) {
        Pack pack = new Pack();

        String strippedLine = line.replaceAll("\\s", "");
        Pattern indexPattern = Pattern.compile("(\\d+(?:\\.\\d{1,2})?):");
        Matcher indexMatcher = indexPattern.matcher(strippedLine);
        checkCondition(indexMatcher.find(), "Could not determine weight for line: %s", line);
        pack.setWeight(new BigDecimal(indexMatcher.group(1)));

        Pattern itemPattern = Pattern.compile("\\((\\d+),(\\d+(?:\\.\\d{1,2})?),\\D*(\\d+(?:\\.\\d{1,2})?)\\)");
        Matcher itemMatcher = itemPattern.matcher(strippedLine);
        while (itemMatcher.find()) {
            int index = Integer.parseInt(itemMatcher.group(1));
            BigDecimal weight = new BigDecimal(itemMatcher.group(2));
            BigDecimal cost = new BigDecimal(itemMatcher.group(3));
            pack.getItems().add(new Item(index, weight, cost));
        }
        return pack;
    }
}
