package ui;

import model.Event;
import model.EventLog;
import model.Sale;
import model.SaleItem;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

import static java.lang.Integer.parseInt;

/*
Represents the small graphical user interface (GUI) for the PotterySaleApp.
 */

public class GUI extends JFrame implements ActionListener {

    private static final String JSON_STORE = "./data/sale.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private Sale items;
    private SaleItem item;
    private int itemIndex;
    private SaleItem oldItem;

    private JLabel idLabel;
    private JLabel priceLabel;
    private JLabel descriptionLabel;
    private JLabel theListLabel;

    private JButton addButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JButton printButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton exitButton;
    private JButton addItemButton;
    private JButton updateItemButton;

    private JTextField idText;
    private JTextField priceText;
    private JTextField descriptionText;
    private JTextField updateIdText;
    private JTextField updatePriceText;

    private JPanel mainMenu;
    private JPanel listingsPanel;
    private JPanel updateListingsPanel;
    private JPanel itemDisplayPanel;

    //Constructor
    public GUI() {
        super("Pottery Sale App");
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(600, 400)); // sets the x- and y- dimension of frame

        initializeMenuPanel();
        initializeItemDisplayPanel();
        initializeItemPanel();
        initializeUpdateItemPanel();

        //LABELS
        idLabel = new JLabel("ID");
        priceLabel = new JLabel("Price");
        descriptionLabel = new JLabel("Description");

        JLabel welcomeLabel = new JLabel("Hello the Pottery Club treasurer!");
        addLabel(welcomeLabel);
        addImageToLabel(welcomeLabel);

        initializeMainMenuButtons();
        addButtons(addButton, deleteButton, updateButton, printButton, saveButton, loadButton, exitButton);
        addActionToButton();
        pack();
        mainMenu.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: add the hand-wave image to the welcome note on the main menu
    private void addImageToLabel(JLabel j) {
        j.setIcon(new ImageIcon("./data/Emoji_handwave.png"));
        mainMenu.add(j);
    }

    // MODIFIES: this
    // EFFECTS: displays the panel with the current items
    public void initializeItemDisplayPanel() {
        JLabel image = new JLabel();
        itemDisplayPanel = new JPanel();
        theListLabel.setPreferredSize(new Dimension(400, 350));
        JScrollPane scroll = new JScrollPane(theListLabel);
        scroll.setBorder(BorderFactory.createLineBorder(Color.lightGray));
        JButton mainMenuButton = new JButton("Return to Main Menu");
        mainMenuButton.setActionCommand("Return to main menu");
        mainMenuButton.addActionListener(this);
        addMenuButton(mainMenuButton, itemDisplayPanel);

        itemDisplayPanel.setBackground(new Color(149, 125, 173));
        itemDisplayPanel.add(image);
        itemDisplayPanel.add(scroll);
    }


    // EFFECTS: initializes the menu panel
    public void initializeMenuPanel() {
        mainMenu = new JPanel();
        mainMenu.setBackground(new Color(149, 125, 173));
        add(mainMenu);
        theListLabel = new JLabel();
        theListLabel.setText("No items available");
        theListLabel.setVerticalAlignment(SwingConstants.TOP);
    }

    // EFFECTS: adds the menu button to the panel
    public void addMenuButton(JButton btn, JPanel panel) {
        btn.setFont(new Font("Calibri", Font.BOLD, 12));
        btn.setForeground(Color.black);
        panel.add(btn);
        pack();
        setVisible(true);
    }

    // EFFECTS: sets the font attributes and adds labels to the main menu
    private void addLabel(JLabel j1) {
        j1.setFont(new Font("Calibri", Font.PLAIN, 28));
        mainMenu.add(j1);
    }

    // EFFECTS: initializes the buttons for the main menu
    public void initializeMainMenuButtons() {
        addButton = new JButton("Add pottery item");
        deleteButton = new JButton("Remove pottery item");
        updateButton = new JButton("Update item price");
        printButton = new JButton("Print list");
        saveButton = new JButton("Save list");
        loadButton = new JButton("Load list");
        exitButton = new JButton("Exit App");
    }

    // MODIFIES: this
    // EFFECTS: adds the individual buttons to the mainMenu
    public void addOneButton(JButton btn, JPanel panel) {
        btn.setFont(new Font("Calibri", Font.PLAIN, 20));
        setVisible(true);
        panel.add(btn);

        pack();
    }

    // EFFECTS: add all buttons to the mainMenu
    public void addButtons(JButton addButton, JButton deleteButton, JButton updateButton, JButton printButton,
                           JButton saveButton, JButton loadButton, JButton exitButton) {
        addOneButton(addButton, mainMenu);
        addOneButton(deleteButton, mainMenu);
        addOneButton(updateButton, mainMenu);
        addOneButton(printButton, mainMenu);
        addOneButton(saveButton, mainMenu);
        addOneButton(loadButton, mainMenu);
        addOneButton(exitButton, mainMenu);
    }

    // EFFECTS: sets the action listeners to the individual buttons
    public void addActionToButton() {
        addButton.addActionListener(this);
        addButton.setActionCommand("Add item to the list");
        deleteButton.addActionListener(this);
        deleteButton.setActionCommand("Remove");
        updateButton.addActionListener(this);
        updateButton.setActionCommand("Update");
        printButton.addActionListener(this);
        printButton.setActionCommand("Print the list");
        saveButton.addActionListener(this);
        saveButton.setActionCommand("Save the list");
        loadButton.addActionListener(this);
        loadButton.setActionCommand("Load the list");
        exitButton.addActionListener(this);
        exitButton.setActionCommand("Exit App");
    }

    // EFFECTS: define the action when a button is selected
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add item to the list")) {
            initializeListingsPanel();
            addItemToList();
            playSound("./data/kaching.wav");
        } else if (e.getActionCommand().equals("Remove")) {
            removeItemFromList();
        } else if (e.getActionCommand().equals("Update")) {
            initializeUpdateListingsPanel();
            updateItemToList();
        } else if (e.getActionCommand().equals("Print the list")) {
            initializeItemListPanel();
        } else if (e.getActionCommand().equals("Save the list")) {
            saveList();
        } else if (e.getActionCommand().equals("Load the list")) {
            loadList();
        } else if (e.getActionCommand().equals("Exit App")) {
            for (Event event : EventLog.getInstance()) {
                System.out.println(event);
            }
            System.exit(0);
        } else if (e.getActionCommand().equals("Return to main menu")) {
            returnToMainMenu();
        }
    }

    // EFFECTS: initializes the listings panel, set visibility to listings panel only
    private void initializeListingsPanel() {
        add(listingsPanel);
        listingsPanel.setVisible(true);
        updateListingsPanel.setVisible(false);
        mainMenu.setVisible(false);
        itemDisplayPanel.setVisible(false);
    }

    // EFFECTS: initializes the update listings panel, set visibility to update listings panel only
    private void initializeUpdateListingsPanel() {
        add(updateListingsPanel);
        listingsPanel.setVisible(false);
        updateListingsPanel.setVisible(true);
        mainMenu.setVisible(false);
        itemDisplayPanel.setVisible(false);
    }

    // EFFECTS: initializes the return, set visibility to mainMenu panel only
    private void returnToMainMenu() {
        listingsPanel.setVisible(false);
        updateListingsPanel.setVisible(false);
        mainMenu.setVisible(true);
        itemDisplayPanel.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: creates the panel for listing the new item
    private void initializeItemPanel() {
        listingsPanel = new JPanel();
        JButton mainMenuButton = new JButton("Return to Main Menu");
        mainMenuButton.setActionCommand("Return to main menu");
        mainMenuButton.setForeground(Color.black);
        mainMenuButton.addActionListener(this);
        addMenuButton(mainMenuButton, listingsPanel);

        createUserTypingPage();
        addLabelsToListings();
    }

    // MODIFIES: this
    // EFFECTS: creates the panel for listing the new item
    private void initializeUpdateItemPanel() {
        updateListingsPanel = new JPanel();
        JButton mainMenuButton = new JButton("Return to Main Menu");
        mainMenuButton.setActionCommand("Return to main menu");
        mainMenuButton.setForeground(Color.black);
        mainMenuButton.addActionListener(this);
        addMenuButton(mainMenuButton, updateListingsPanel);

        createUpdateTypingPage();
        addLabelsToUpdate();
    }

    // EFFECTS: adds the listing page on screen, set others invisible
    private void initializeItemListPanel() {
        add(itemDisplayPanel);
        itemDisplayPanel.setVisible(true);
        mainMenu.setVisible(false);
        listingsPanel.setVisible(false);
        updateListingsPanel.setVisible(false);
    }

    // MODIFIES: this
    // EFFECTS: creates the user typing fields
    private void createUserTypingPage() {
        addItemButton = new JButton("Add item to the list");
        addItemButton.setActionCommand("Add item to the list");
        addItemButton.setForeground(Color.black);
        addItemButton.addActionListener(this);

        idLabel = new JLabel("Please enter the item ID#");
        idText = new JTextField(40);
        priceLabel = new JLabel("Please enter the item price ($ CAD)");
        priceText = new JTextField(40);
        descriptionLabel = new JLabel("Please enter the item description");
        descriptionText = new JTextField(40);
        listingLabelAttributes();
    }

    // MODIFIES: this
    // EFFECTS: creates the user typing fields for update an item
    private void createUpdateTypingPage() {
        updateItemButton = new JButton("Update this item");
        updateItemButton.setActionCommand("Update");
        updateItemButton.setForeground(Color.black);
        updateItemButton.addActionListener(this);

        idLabel = new JLabel("Please enter the item ID#");
        updateIdText = new JTextField(40);
        priceLabel = new JLabel("What is the new price?");
        updatePriceText = new JTextField(40);
        listingLabelAttributes();
    }

    // EFFECTS: adds the labels to the listingsPage
    public void addLabelsToListings() {
        listingsPanel.add(addItemButton);

        listingsPanel.add(idLabel);
        listingsPanel.add(idText);
        listingsPanel.add(priceLabel);
        listingsPanel.add(priceText);
        listingsPanel.add(descriptionLabel);
        listingsPanel.add(descriptionText);
    }

    // EFFECTS: adds the labels to the updateListingPage
    private void addLabelsToUpdate() {
        updateListingsPanel.add(updateItemButton);

        updateListingsPanel.add(idLabel);
        updateListingsPanel.add(updateIdText);
        updateListingsPanel.add(priceLabel);
        updateListingsPanel.add(updatePriceText);
    }

    // EFFECTS: sets the labels and text fields
    private void listingLabelAttributes() {
        addItemButton.setForeground(Color.black);
        addItemButton.setFont(new Font("Calibri", Font.BOLD, 12));

        idLabel.setFont(new Font("Calibri", Font.BOLD, 30));
        priceLabel.setFont(new Font("Calibri", Font.BOLD, 30));
        descriptionLabel.setFont(new Font("Calibri", Font.BOLD, 30));
    }

    // MODIFIES: this
    // EFFECTS: Adds the new item to the list, and prints output message in console
    // wrap text reference: https://stackoverflow.com/questions/2420742/make-a-jlabel-wrap-its-text-
    // by-setting-a-max-width
    private void addItemToList() {
        try {
            item = new SaleItem(idText.getText(), priceText.getText(), descriptionText.getText());
            items.addItem(item);
            System.out.println("Item has been successfully added.");
            theListLabel.setText("<html><pre>Current list: \n" + items.printSale() + "\n</pre></html>");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input, please try again");
        } catch (IndexOutOfBoundsException e) {
            theListLabel.setText("Please initialize listings file before proceeding");
        }
    }

    // MODIFIES: this
    // EFFECTS: update the item according to ID, and prints output message in console
    private void updateItemToList() {
        try {
            itemIndex = parseInt(updateIdText.getText());
            oldItem = items.getItem(itemIndex);
            oldItem.setItemPrice(updatePriceText.getText());
            System.out.println("Item has been successfully updated.");
            theListLabel.setText("<html><pre>Current list: \n" + items.printSale() + "\n</pre></html>");
        } catch (NumberFormatException e) {
            System.out.println("Invalid input, please try again");
        } catch (IndexOutOfBoundsException e) {
            theListLabel.setText("Invalid input, no item with such ID");
        }
    }

    // MODIFIES: this
    // EFFECTS: Removes the selected item, and prints message in console
    private void removeItemFromList() {
        // todo
        try {
            items.removeItem(item);
            System.out.println("Item is successfully removed.");
        } catch (IndexOutOfBoundsException e) {
            theListLabel.setText("Invalid ID, please try again");
        } catch (NullPointerException e) {
            System.out.println("Please add an item first");
        }
    }

    // EFFECTS: plays sound
    // REFERENCE: https://techhelpnotes.com/swing-best-way-to-get-sound-on-button-press-for-a-java-calculator-2/
    // Sound clips were downloaded free on the internet.
    public void playSound(String soundName) {
        try {
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception exception) {
            System.out.println("There is an error playiny the sound");
        }
    }

    // EFFECTS: saves the list with the added items
    private void saveList() {
        try {
            jsonWriter.open();
            jsonWriter.write(items);
            jsonWriter.close();
            JOptionPane.showMessageDialog(null, "Saved to file:" + JSON_STORE);
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the list from the saved file
    private void loadList() {
        try {
            items = jsonReader.read();
            JOptionPane.showMessageDialog(null, "Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file " + JSON_STORE);
        } catch (NullPointerException e) {
            System.out.println("Please add an item first");
        }
    }
}

