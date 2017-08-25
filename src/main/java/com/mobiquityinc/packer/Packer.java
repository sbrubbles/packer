package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The entry point class of the application, responsible for receiving the input file from the user and executing the functions to
 * present the solution of the problem.
 */
public class Packer {
    private static final Logger LOGGER = Logger.getLogger(Packer.class.getName());
    private static final PackProcessor PACK_PROCESSOR = new PackProcessor();

    /**
     * Main method of the application.
     * <p>
     * It will receive an array of arguments from the user for the application execution. Uses the first item from the arguments as the
     * file path tpo be parsed into packs. If the arguments have more than one item, the remaining ones will be ignored; if it doesn't
     * contain any items, it will throw an {@link APIException}.
     *
     * @param args the program arguments passed by the user
     * @throws APIException if the user does not specify any argument
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            LOGGER.log(Level.SEVERE, "You must provide a file path");
        } else {
            try {
                System.out.println(pack(args[0]));
            } catch (APIException e) {
                LOGGER.log(Level.SEVERE, e.getMessage(), e);
            }
        }
    }

    /**
     * Accepts a file path as string and returns a string containing one line for each pack and each line containing the selected item's
     * indexes for that pack.
     *
     * @param filePath the file path for the imput file
     * @return the list of resolutions for each of the packs
     */
    public static String pack(String filePath) {
        return PACK_PROCESSOR.processFile(filePath);
    }
}
