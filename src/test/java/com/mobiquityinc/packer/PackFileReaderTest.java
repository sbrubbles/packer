package com.mobiquityinc.packer;

import com.mobiquityinc.PackMocker;
import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.Pack;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;

import static com.mobiquityinc.PackMocker.item;
import static com.mobiquityinc.PackMocker.pack;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;

/**
 * Test cases for validating the {@link PackFileReader} class.
 *
 * @see PackFileReader
 */
@RunWith(MockitoJUnitRunner.class)
public class PackFileReaderTest {

    @InjectMocks
    private PackFileReader fileReader;

    /**
     * Tests if the class can read the contents of {@link PackMocker#VALID_INPUT} and correctly parse the results
     *
     * @see PackFileReader#readFile(String)
     */
    @Test
    public void testReadFile() {
        List<Pack> packs = fileReader.readFile(PackMocker.VALID_INPUT);

        // 16 : (1,16.20,€27) (2,10.44,€78)
        Pack first = pack("16", "0",
                item(1, "16.20", "27"),
                item(2, "10.44", "78"));

        // 76 : (1,67.12,€1) (2,19.36,€51)
        Pack second = pack("76", "0",
                item(1, "67.12", "1"),
                item(2, "19.36", "51"));

        assertThat(2, equalTo(packs.size()));
        assertThat(first, equalTo(packs.get(0)));
        assertThat(second, equalTo(packs.get(1)));
    }

    /**
     * Tests the behavior of the class when it receives as parameter a path to a tile that doesn't exist.
     *
     * @see PackFileReader#readFile(String)
     */
    @Test
    public void testReadFileFileNotExists() {
        try {
            fileReader.readFile("asdf");
        } catch (Exception e) {
            assertThat(e, instanceOf(APIException.class));
            assertThat(e.getCause(), instanceOf(IOException.class));
        }
    }

    /**
     * Tests the behavior of the class when it receives as parameter a path to a directory.
     *
     * @see PackFileReader#readFile(String)
     */
    @Test
    public void testReadFileDirectory() {
        try {
            fileReader.readFile("src");
        } catch (Exception e) {
            assertThat(e, instanceOf(APIException.class));
            assertThat(e.getCause(), instanceOf(IOException.class));
        }
    }

    /**
     * Test the behavior of the class when it receives a file that is not valid.
     *
     * @see PackFileReader#readFile(String)
     */
    @Test(expected = APIException.class)
    public void testReadFileInvalid() {
        fileReader.readFile(PackMocker.INVALID_INPUT);
    }
}
