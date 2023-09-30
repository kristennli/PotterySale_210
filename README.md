# Pottery Sale Inventory Management

## Project Overview
The UBC Pottery Club has over 100 members, and we hold pottery sales quarterly at either beginning or the end the term.
Currently, we use Excel sheet to keep track of our inventory including the unique item identifier (for example, 
a two-digit code, for example, 01), item description, the value of the item. The club treasurer manually pools and 
inputs each item from members.

The current method for tracking the inventory is **tedious and human-error prone**. Therefore, allowing the treasurer to 
edit the sale list can drastically reduce the workload. This **inventory management application** allows users to 
manage their inventories for the sale.

*Who will use this application*

This application is intended for people who would like to manage their inventory with ease. Both club members and the
club treasurer can use it to help manage inventory.

*Why I am interested in this project*

As a regular seller participating at the pottery sale, inventory management takes significant amount of time. Similarly,
the treasurer always has a hard time managing the inventory for all members. Therefore, I can see the potential of using 
this application to help reduce the workload and make inventory management with ease.

*Features of this application can include*

- add and remove items from the list
- update an item price on the list if the seller changes their mind
- view the inventory list with item ID, price and description of the pottery pieces
- save and reload the list after quitting the application (data persistence) 


## User Stories
- As a user, I want to be able to add a pottery item to the sale
- As a user, I want to be able to remove a pottery item to the sale
- As a user, I want to be able to update a pottery item price from the list
- As a user, I want to be able to view the list of pottery items
- As a user, I want to be able to save the list to file
- As a user, I want to be able to load the list from file
*Phase3 Task 3
- As a user, I want to be able to add multiple pottery items to the sale
- As a user, I want to be able to update a pottery item price from the list
- As a user, I want to be able to load and save the state of the application

## Instructions for Grader
- always click on the button labelled "Load list" to load your previous saved file
- select "ok" from the pop-up window
- click the button labelled "Add pottery item" 
- this will trigger the kaching sound
- input "1" for item ID, input "10" for item price, and input "cup" for description
- click the button labelled "Add item to the list"
- click the button labelled "Return to Main Menu"
- click the button labelled "Print List" to view the updated item list
- click the button labelled "Return to Main Menu"
- click the button labelled "Save list"
- select "ok" from the pop-up window
- click the button "Exit App"
- rerun Main
- click on "Load list"
- then we can update the item price by clicking on the button labelled "Update item price"
- input "1" for item ID, and input "20" for the new price
- click on "Update this item"
- then click on "Return to Main Menu"
- you can see the newly updated price by clicking on "Print list"
- finally close the app by clicking on "Exit App"
- for Jessica: I still couldn't figure out why the display pottery list panel will not show the item list upon loading without either add or update an item ;(
## Phase 4: Task 2
Wed Aug 10 12:10:22 PDT 2022
Added new pottery item:
Wed Aug 10 12:10:22 PDT 2022
Added new pottery item: mug
Wed Aug 10 12:10:22 PDT 2022
Added new pottery item: mug
Wed Aug 10 12:10:22 PDT 2022
Added new pottery item: bowl
Wed Aug 10 12:10:22 PDT 2022
Added new pottery item: teapot
Wed Aug 10 12:10:22 PDT 2022
Added new pottery item: bowl
Wed Aug 10 12:10:22 PDT 2022
Added new pottery item: jug
Wed Aug 10 12:10:22 PDT 2022
Added new pottery item: teacup
Wed Aug 10 12:10:22 PDT 2022
Added new pottery item: large vase
Wed Aug 10 12:10:43 PDT 2022
Added new pottery item: plate

## Phase 4: Task 3
For refactoring, I found a lot of repeating codes in the GUI class. For example, initializeUpdatelistingsPanel 
and initializeListingsPanel. They all have the same 4 panels but with different setVisible. Therefore, refactoring will
make the codes more simple and reader-friendly.

For Sale class, for example in the printSale() method, Iterator might help reduce coupling. Hence, Sale can implement Iterable. I can use iterator for the update and remove methods.