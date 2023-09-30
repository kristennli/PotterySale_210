package model;

import persistence.Writable;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
Represents a sale list with the individual pottery pieces.
 */

public class Sale implements Writable {
    private ArrayList<SaleItem> listOfItems;

    //Constructor
    // EFFECTS: constructs a sale list with pottery items. The list is empty when the list
    //          is initialized.
    public Sale() {
        listOfItems = new ArrayList<>();
    }

    // EFFECTS: prints out the pottery items in the list
    public String printSale() {
        String listings = "";
        for (int i = 0; i < listOfItems.size(); i++) {
            listings += "Item ID#" + listOfItems.get(i).getItemID() + "\t"
                    + "Price(CAD):" + listOfItems.get(i).getItemPrice() + "\t"
                    + "Description:" + listOfItems.get(i).getItemDescription() + "\n";
        }
        return listings;
    }

    // REQUIRES: the list does not already contain the item ID
    // MODIFIES: this
    // EFFECTS: adds the item to the list if it has not been listed.
    public void addItem(SaleItem saleItem) {
        if (!contains(saleItem)) {
            EventLog.getInstance().logEvent(new Event("Added new pottery item: "
                    + saleItem.getItemDescription()));
            listOfItems.add(saleItem);
        }
    }

    // Effects: get the size of the list.
    public int getSize() {
        return listOfItems.size();
    }

    // EFFECTS: remove the item if it has already been added
    public void removeItem(SaleItem saleItem) {
        if (contains(saleItem)) {
            listOfItems.remove(saleItem);
        }
    }

    // EFFECTS: returns true if the list contains the item. False otherwise.
    public boolean contains(SaleItem saleItem) {
        for (SaleItem s : listOfItems) {
            if (checkEquals(s, saleItem)) {
                return true;
            }
        }
        return false;
    }

    // REQUIRES: the list is not empty
    // EFFECTS: returns true if the two IDs are the same, otherwise false
    public boolean checkEquals(SaleItem s, SaleItem saleItem) {
        return s.getItemID().equals(saleItem.getItemID());
    }

    // EFFECTS: returns true if the list is empty, otherwise false.
    public boolean isEmpty() {
        if (listOfItems.isEmpty()) {
            return true;
        }
        return false;
    }

    // EFFECTS: returns an unmodifiable list of items.
    public List<SaleItem> getListOfItems() {
        return Collections.unmodifiableList(listOfItems);
    }

    // EFFECTS: returns the item from the list at a specific index
    public SaleItem getItem(int i) throws IndexOutOfBoundsException {
        if (listOfItems.get(i) == null) {
            throw new IndexOutOfBoundsException();
        }
        return listOfItems.get(i);
    }

    @Override
    // EFFECTS: write a json object with the list
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("items", saleItemsToJson());
        return json;
    }

    // EFFECTS: returns items in this sale list as a JSON array
    private JSONArray saleItemsToJson() {
        JSONArray jsonArray = new JSONArray();

        for (SaleItem s : listOfItems) {
            jsonArray.put(s.toJson());
        }
        return jsonArray;
    }
}

