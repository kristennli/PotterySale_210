package ui;

import model.SaleItem;
import model.Sale;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

/*
Represents the application for the PotterySaleApp.
 */

// credits for reference: TellerApp
public class PotterySaleApp {
    private static final String JSON_STORE = "./data/sale.json";
    private Scanner input;
    private Sale items;
    private SaleItem item;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: runs the pottery sale application
    // credits: this program referenced the TellerApp
    public PotterySaleApp() {
        input = new Scanner(System.in);
        item = new SaleItem("123","12","mugs");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        runPotterySale();
    }

    // EFFECTS: runs the app
    private void runPotterySale() {
        boolean keepGoing = true;
        String command = null;
        System.out.println("Hello the Pottery Club treasurer");

        init();

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();

            if (command.equals("q")) {
                keepGoing = false;
            } else {
                processCommand(command);
            }
        }
        System.out.println("See you next time!");
    }

    // EFFECTS: displays the available options to the user
    private void displayMenu() {
        System.out.println("\nSelect from:");
        System.out.println("\ta -> add an item");
        System.out.println("\tr -> remove an item");
        System.out.println("\tu -> update the price for an item");
        System.out.println("\tp -> print the list");
        System.out.println("\ts -> save list to file");
        System.out.println("\tl -> load list from file");
        System.out.println("\tq -> quit");
    }

    // MODIFIES: this
    // EFFECTS: processes the user command
    // reference for the processCommand(): TellerApp
    private void processCommand(String command) {
        if (command.equals("a")) {
            addItem(item);
        } else if (command.equals("r")) {
            removeItem(item);
        } else if (command.equals("u")) {
            updateList();
        } else if (command.equals("p")) {
            printList();
        } else if (command.equals("s")) {
            saveList();
        } else if (command.equals("l")) {
            loadList();
        } else {
            System.out.println("Invalid input, please try again");
        }
    }

    // EFFECTS: prints out the pottery items in the list
    private void printList() {
        System.out.println(items.printSale());
    }


    // REQUIRES: the list is not empty.
    // MODIFIES: this
    // EFFECTS: displays the options for updating an item.
    private void updateList() {
        printList();
        System.out.println("Would you like to update the price for any of the items (yes/no)?");
        updateMenu();
    }

    // EFFECTS: process the user commands if they want to update the price for an item.
    private void updateMenu() {
        String command;
        input = new Scanner((System.in));
        command = input.next();
        command = command.toLowerCase();

        if (command.equals("yes")) {
            updateItem();
        } else if (command.equals("no")) {
            System.out.println("Returning to the previous menu");
        }
    }

    // REQUIRES: the list is not empty
    // MODIFIES: oldItem
    // EFFECTS: update the price of a selected item in the list
    private void updateItem() {
        input = new Scanner(System.in);
        String newPrice;
        SaleItem oldItem;
        int itemIndex;

        items.getListOfItems();
        System.out.println("Please enter the ID of the item you would like to change");

        itemIndex = input.nextInt();
        oldItem = items.getItem(itemIndex);

        System.out.println("What is the new price?");
        newPrice = input.next();
        oldItem.setItemPrice(newPrice);
        System.out.println("The price has been updated.");
    }

    // MODIFIES: this
    // EFFECTS: initializes sale items
    public void init() {
        items = new Sale();
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    // REQUIRES: the list does not already contain the item ID
    // MODIFIES: this
    // EFFECTS: adds the item to the end of the list and returns true. False otherwise.
    public Sale addItem(SaleItem saleItem) {

        System.out.println("Enter the item ID.");
        String itemID = input.next();

        System.out.println("Enter the price.");
        String itemPrice = input.next();

        System.out.println("Enter the description.");
        String itemDescription = input.next();

        items.addItem(new SaleItem(itemID,itemPrice,itemDescription));
        System.out.println("Item has been successfully added.");
        return this.items;
    }

    // REQUIRES: the list is not empty
    // MODIFIES: this
    // EFFECTS: removes the item by ID
    public void removeItem(SaleItem saleItem) {
        input = new Scanner(System.in);
        SaleItem oldItem;
        int itemIndex;

        items.getListOfItems();
        System.out.println("Please enter the ID of the item you would like to remove");
        printList();

        itemIndex = input.nextInt();
        oldItem = items.getItem(itemIndex);

        items.removeItem(oldItem);
        System.out.println("Item is successfully removed.");
    }

    // EFFECTS: saves the sale list to file.
    // reference: JsonSerializationDemo-WorkRoomApp.
    private void saveList() {
        try {
            jsonWriter.open();
            jsonWriter.write(items);
            jsonWriter.close();
            System.out.println("Saved to file:" + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads sale from file
    private void loadList() {
        try {
            items = jsonReader.read();
            System.out.println("Loaded from:" + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}