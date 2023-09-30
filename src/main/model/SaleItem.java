package model;

/*
Represent a pottery item for sale.
 */
import persistence.Writable;
import org.json.JSONObject;

// Represents a pottery item having an ID, price and description
public class SaleItem implements Writable {
    private String itemID;
    private String itemPrice;
    private String itemDescription;

    //Constructor
    // REQUIRES: Price >= 0
    // MODIFIES: this
    // EFFECTS: creates a list of items with name, ID and price.
    //          The sold status should be false when item is constructed.
    public SaleItem(String itemID, String itemPrice, String itemDescription) {
        this.itemID = itemID;
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
    }

    //getters
    // EFFECTS: returns the item ID.
    public String getItemID() {
        return this.itemID;
    }

    // EFFECTS: returns the item description.
    public String getItemDescription() {
        return itemDescription;
    }

    // EFFECTS: returns the price of the item.
    public String getItemPrice() {
        return itemPrice;
    }

    //setters
    // EFFECTS: sets the item price
    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    // EFFECTS: returns string representation of the pottery item.
    @Override
    public String toString() {
        return "Item# " + this.getItemID() + " Price(CAD): " + this.getItemPrice() + " Description: "
                + this.getItemDescription();
    }

    // EFFECTS: writes a json object for the item
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("itemID", itemID);
        json.put("itemPrice", itemPrice);
        json.put("itemDescription", itemDescription);
        return json;
    }
}
