package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SaleTest {
    private Sale testSale;
    private SaleItem item1;
    private SaleItem item2;
    private SaleItem item3;

    @BeforeEach
    public void runBefore() {
        testSale = new Sale();
        item1 = new SaleItem("101", "25", "White mug");
        item2 = new SaleItem("102", "50", "Large plate");
        item3 = new SaleItem("103", "20", "Medium bowl");
    }

    @Test
    public void testConstructor() {
        assertTrue(testSale.isEmpty());
    }

    @Test
    public void testAddItem() {
        testSale.addItem(item1);
        assertFalse(testSale.isEmpty());
        assertTrue(testSale.contains(item1));
    }

    @Test
    public void testAddTheSameTwice() {
        testSale.addItem(item1);
        assertTrue(testSale.contains(item1));
        assertEquals(1, testSale.getSize());
        testSale.addItem(item1);
        assertEquals(1, testSale.getSize());
        testSale.addItem(item2);
        assertEquals(2, testSale.getSize());
    }

    @Test
    public void testRemoveItem() {
        testSale.addItem(item1);
        assertTrue(testSale.contains(item1));
        testSale.removeItem(item1);
        assertFalse(testSale.contains(item1));
    }

    @Test
    public void testRemoveItemNotExist() {
        testSale.addItem(item1);
        assertTrue(testSale.contains(item1));
        assertEquals(1, testSale.getSize());
        testSale.addItem(item2);
        assertEquals(2, testSale.getSize());
        testSale.removeItem(item3);
        assertEquals(2, testSale.getSize());
    }

    @Test
    public void testGetSize() {
        assertEquals(0, testSale.getSize());
        testSale.addItem(item1);
        assertEquals(1, testSale.getSize());
    }

    @Test
    public void testContains() {
        assertFalse(testSale.contains(item1));
        testSale.addItem(item1);
        assertTrue(testSale.contains(item1));
        assertFalse(testSale.contains(item2));
    }

    @Test
    public void testCheckEquals() {
        SaleItem item4 = new SaleItem("1", "10", "plate");
        SaleItem item5 = new SaleItem("101", "25", "White mug");
        assertTrue(testSale.checkEquals(item1, item5));
        assertFalse(testSale.checkEquals(item1, item4));
    }

//    @Test
//    public void testPrintSale() {
//        testSale.addItem(item1);
//        testSale.printSale();
//        assertN("Item# 101 Price(CAD): 25 Description: White mug", testSale.printSale());
//    }

    @Test
    public void testIsEmpty() {
        testSale.addItem(item1);
        assertFalse(testSale.isEmpty());
    }

    @Test
    public void testGetListOfItems() {
        assertEquals(0, testSale.getListOfItems().size());
        testSale.addItem(item1);
        testSale.addItem(item2);
        assertEquals(2, testSale.getListOfItems().size());
    }

    // IndexOutOfBoundsException reference: github pkhiani's AuctionApp
    @Test
    public void testGetItemExceptionCaught() throws IndexOutOfBoundsException {
        testSale.addItem(item1);
        try {
            assertNull(testSale.getItem(1));
        } catch (IndexOutOfBoundsException e) {
            //all good
        }
    }

    @Test
    public void testGetItemExceptionFail() throws IndexOutOfBoundsException {
        testSale.addItem(item1);
        testSale.printSale();
        try {
            assertEquals(testSale.getItem(0), item1);
        } catch (IndexOutOfBoundsException e) {
            fail("Unexpected exception thrown");
        }
    }


}
