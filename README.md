C.  Customize the HTML user interface for your customer’s application. The user interface should include the shop name, the product names, and the names of the parts.


Note: Do not remove any elements that were included in the screen. You may add any additional elements you would like or any images, colors, and styles, although it is not required.

C: Line 14-41 Added styling to the mainscreen.html 


D.  Add an “About” page to the application to describe your chosen customer’s company to web viewers and include navigation to and from the “About” page and the main screen.
D: Copied and pasted the first line 1-62 into the about.html from mainscreen.html then added button as well as business information line 62-71. Added aboutcontroller for mapping for button 

E.  Add a sample inventory appropriate for your chosen store to the application. You should have five parts and five products in your sample inventory and should not overwrite existing data in the database.

E: Edited code that was already provided, added 5 parts and products as well as and if statement to make sure they display when the page is empty line 27-66.


Note: Make sure the sample inventory is added only when both the part and product lists are empty. When adding the sample inventory appropriate for the store, the inventory is stored in a set so duplicate items cannot be added to your products. When duplicate items are added, make a “multi-pack” part.


F.  Add a “Buy Now” button to your product list. Your “Buy Now” button must meet each of the following parameters:
•  The “Buy Now” button must be next to the buttons that update and delete products.
•  The button should decrement the inventory of that product by one. It should not affect the inventory of any of the associated parts.
•  Display a message that indicates the success or failure of a purchase.
F: Added buy button line 109-112 in the mainscreen.html and a controller called BuyProductController it nativagates to the PurchaseErro.html and PurchaseSuccess.html


G.  Modify the parts to track maximum and minimum inventory by doing the following:
•  Add additional fields to the part entity for maximum and minimum inventory.
•  Modify the sample inventory to include the maximum and minimum fields.
•  Add to the InhousePartForm and OutsourcedPartForm forms additional text inputs for the inventory so the user can set the maximum and minimum values.
•  Rename the file the persistent storage is saved to.
•  Modify the code to enforce that the inventory is between or at the minimum and maximum value.
G: Part.java line 82-96 added to track the max and min inventory as well as updated the BootstrapData to add min and max values to parts line 38-42. Line 25-29 in outsourcedpartform.html added mix and max inventory and line 22-26 in inhousepartform.html added min and max inventory section. Renamed spring-boot-h2-db102 to spring-boot-h2-dblogan_219
Went back and added min and max fields to the productform and a error display. 


H.  Add validation for between or at the maximum and minimum fields. The validation must include the following:
•  Display error messages for low inventory when adding and updating parts if the inventory is less than the minimum number of parts.
•  Display error messages for low inventory when adding and updating products lowers the part inventory below the minimum.
•  Display error messages when adding and updating parts if the inventory is greater than the maximum.
H: Added line 29-53 in AddOutsourcedpart and added line 30-54 for if inventory is below min or above max inventory. line 29-67 EnufPartValidator if statement returns false when it falls under min or above max. 
Added code to the outsouced and inhouse controller to ensure the min and max requirements are met in line 45-56.
Also added code into add product controller to display error if the part being added would go over max or under the min. lines 103-125.


I.  Add at least two unit tests for the maximum and minimum fields to the PartTest class in the test package.
I: Line 163-177 in Parttest to created MinInv and MaxInv test.


J.  Remove the class files for any unused validators in order to clean your code.
J: Ran the findusuage to view which validators were being used.