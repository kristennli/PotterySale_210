package persistence;

import model.SaleItem;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkSale(String itemID, String itemPrice,String itemDescription, SaleItem saleItem) {
        assertEquals(itemID, saleItem.getItemID());
        assertEquals(itemPrice, saleItem.getItemPrice());
        assertEquals(itemDescription, saleItem.getItemDescription());
    }
}
