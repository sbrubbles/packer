package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import com.mobiquityinc.model.Pack;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static com.mobiquityinc.PackMocker.item;
import static com.mobiquityinc.PackMocker.pack;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Test cases for validating the {@link PackProcessor} class.
 *
 * @see PackProcessor
 */
@RunWith(MockitoJUnitRunner.class)
public class PackProcessorTest {

    @Mock
    private PackFileReader fileReaderMock;
    @InjectMocks
    private PackProcessor packProcessor;

    /**
     * Basic validation for the Item picking algorithm
     *
     * @see PackProcessor#processFile(String)
     */
    @Test
    public void testProcessFile() {
        List<Pack> packs = Collections.singletonList(
                pack("40", "0",
                        item(1, "17.00", "92"),
                        item(2, "21.00", "23"),
                        item(3, "13.00", "49"),
                        item(4, "37.00", "93"),
                        item(5, "5.00", "81"),
                        item(6, "5.00", "1"),
                        item(7, "9.00", "97")));

        when(fileReaderMock.readFile(anyString()))
                .thenReturn(packs);

        String result = packProcessor.processFile("");
        assertThat(result, equalTo("1,5,6,7"));
    }

    /**
     * Basic validation for the Item picking algorithm
     *
     * @see PackProcessor#processFile(String)
     */
    @Test
    public void testProcessFile2() {
        List<Pack> packs = Collections.singletonList(
                pack("13", "0",
                        item(1, "18.00", "38"),
                        item(2, "8.00", "93"),
                        item(3, "12.00", "75"),
                        item(4, "15.00", "88"),
                        item(5, "8.00", "62"),
                        item(6, "5.00", "30")));

        when(fileReaderMock.readFile(anyString()))
                .thenReturn(packs);

        String result = packProcessor.processFile("");
        assertThat(result, equalTo("2,6"));
    }

    /**
     * Implementations with a single loop for matching and getting unmatched items will result into choosing 2,4 instead of the right
     * solution 1,3,4.
     *
     * @see PackProcessor#processFile(String)
     */
    @Test
    public void testProcessFile3() {
        List<Pack> packs = Collections.singletonList(
                pack("86", "0",
                        item(1, "30.00", "74"),
                        item(2, "74.00", "79"),
                        item(3, "35.00", "51"),
                        item(4, "12.00", "95")));

        when(fileReaderMock.readFile(anyString()))
                .thenReturn(packs);

        String result = packProcessor.processFile("");
        assertThat(result, equalTo("1,3,4"));
    }

    /**
     * Tests the validation of the package max weight (100)
     *
     * @see PackProcessor#processFile(String)
     */
    @Test(expected = APIException.class)
    public void testProcessFileValidatePackWeight() {
        List<Pack> packs = Collections.singletonList(
                pack("101", "0", item(1, "1.00", "1")));

        when(fileReaderMock.readFile(anyString()))
                .thenReturn(packs);

        packProcessor.processFile("");
    }

    /**
     * Tests the validation of the package max item count (15)
     *
     * @see PackProcessor#processFile(String)
     */
    @Test(expected = APIException.class)
    public void testProcessFileValidatePackitemCount() {
        List<Pack> packs = Collections.singletonList(pack("1", "0"));
        IntStream.range(0, 16)
                .forEach(i -> packs.get(0).addItem(item(i + 1, "1.00", "1")));

        when(fileReaderMock.readFile(anyString()))
                .thenReturn(packs);

        packProcessor.processFile("");
    }

    /**
     * Tests the validation of the item max weight (100)
     *
     * @see PackProcessor#processFile(String)
     */
    @Test(expected = APIException.class)
    public void testProcessFileValidateItemWeight() {
        List<Pack> packs = Collections.singletonList(
                pack("1", "0", item(1, "101.00", "1")));

        when(fileReaderMock.readFile(anyString()))
                .thenReturn(packs);

        packProcessor.processFile("");
    }

    /**
     * Tests the validation of the package max cost (100)
     *
     * @see PackProcessor#processFile(String)
     */
    @Test(expected = APIException.class)
    public void testProcessFileValidateItemCost() {
        List<Pack> packs = Collections.singletonList(
                pack("1", "0", item(1, "1.00", "101")));

        when(fileReaderMock.readFile(anyString()))
                .thenReturn(packs);

        packProcessor.processFile("");
    }
}
