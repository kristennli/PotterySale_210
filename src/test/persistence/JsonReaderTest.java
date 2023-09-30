package persistence;

import model.SaleItem;
import model.Sale;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Reference: JsonSerializationDemo
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            Sale s = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // all good
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        JsonReader reader = new JsonReader("./data/testWriterEmptySale.json");
        try {
            Sale s = reader.read();
            assertTrue(s.isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testWriterGeneralSale1.json");
        try {
            Sale s = reader.read();
            List<SaleItem> items = s.getListOfItems();
            assertEquals(2, items.size());
            checkSale("0", "10", "mug", items.get(0));
            checkSale("1", "40", "plate", items.get(1));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
