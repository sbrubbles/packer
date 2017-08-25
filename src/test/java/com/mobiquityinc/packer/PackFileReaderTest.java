package com.mobiquityinc.packer;

import com.mobiquityinc.exception.APIException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;


@RunWith(PowerMockRunner.class)
@PrepareForTest(Files.class)
public class PackFileReaderTest {

    private static final String VALID_INPUT = "src/test/resources/valid.txt";

    @InjectMocks
    private PackFileReader fileReader;

    @Test
    public void testReadFile() throws Exception {
        List<Pack> packs = fileReader.readFile(VALID_INPUT);

        // 16 : (1,16.20,€27) (2,10.44,€78)
        Pack first = pack("16",
                item(1, "16.2", "27"),
                item(2, "10.44", "78"));

        // 76 : (1,67.12,€1) (2,19.36,€51)
        Pack second = pack("76",
                item(1, "67.12", "1"),
                item(2, "19.36", "51"));

        assertThat(2, equalTo(packs.size()));
        assertThat(first, equalTo(packs.get(0)));
        assertThat(second, equalTo(packs.get(1)));
    }

    @Test(expected = APIException.class)
    public void testReadFileIOException() throws Exception {
        mockStatic(Files.class);

        when(Files.readAllLines(any(Path.class)))
                .thenThrow(new IOException());

        verifyStatic();
        fileReader.readFile(VALID_INPUT);
    }

    private static Pack pack(String weight, Item... items) {
        Pack pack = new Pack();
        pack.setWeight(new BigDecimal(weight));
        pack.getItems().addAll(Arrays.asList(items));
        return pack;
    }

    private static Item item(int index, String weight, String cost) {
        return new Item(index, new BigDecimal(weight), new BigDecimal(cost));
    }
}
