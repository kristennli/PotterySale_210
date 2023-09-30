package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SaleItemTest {
    private SaleItem testSaleItem;

    @BeforeEach
    public void runBefore() {
        testSaleItem = new SaleItem("101", "25", "White mug");
    }

    @Test
    public void testConstructor() {
        assertEquals("101", testSaleItem.getItemID());
        assertEquals("25", testSaleItem.getItemPrice());
        assertEquals("White mug", testSaleItem.getItemDescription());
    }

    @Test
    public void testSetItemPrice() {
        testSaleItem = new SaleItem("108", "25", "Espresso cup");
        testSaleItem.setItemPrice("22");
        assertEquals("22", testSaleItem.getItemPrice());
    }

    @Test
    public void testToString() {
        assertEquals("Item# 101 Price(CAD): 25 Description: White mug", testSaleItem.toString());
    }

}
