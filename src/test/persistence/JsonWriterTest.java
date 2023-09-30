package persistence;

import model.SaleItem;
import model.Sale;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Reference: JsonSerializationDemo
public class JsonWriterTest extends JsonTest {

    @Test
    void testWriterInvalidFile() {
        try {
            Sale s = new Sale();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // all good
        }
    }

    @Test
    void testWriterEmptySale() {
        try {
            Sale s = new Sale();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptySale.json");
            writer.open();
            writer.write(s);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptySale.json");
            s = reader.read();
            assertTrue(s.isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralSale() {
        try {
            Sale s = new Sale();
            s.addItem(new SaleItem("0", "10", "mug"));
            s.addItem(new SaleItem("1", "40", "plate"));
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralSale1.json");
            writer.open();
            writer.write(s);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralSale1.json");
            s = reader.read();

            List<SaleItem> items = s.getListOfItems();
            assertEquals(2, items.size());
            checkSale("0", "10", "mug", items.get(0));
            checkSale("1", "40", "plate", items.get(1));
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}
