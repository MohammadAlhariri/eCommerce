TODO list

General Design:
    - Prime Color
    - Dark Prime Color
    - App_logo
    - Add Icon

Seller:
    - Seller AddProduct:
        - change to RelativeLayout
        - corp for imageview in add product

    - Seller HomeActivity:
        - CardView
            - redesign for CardView.
            - corp for Imageview.
            - Price change Equal sign.

    - Seller LoginActivity:
        Modify Toast.

    - Seller LogoutActivity:
        Add Toast When User Logout.

    - In Database Seller:
        - Make phone number and Email unique key.


User:

    - User Registration activity :
        - Modify Toast. Login, cartActivity "Remove Error",
        - Delete comment from the API.
        + when press back forward activity to HomeActivity.
        - Add Toast For Wrong Username or Password

   - HomeActivity:
        - reload HomeActivity every X time.

   - ProductDetailsActivity:
        - corp Imageview
        - Add TextView as counter for Total Price.

    - cartActivity:
        - Remove Order add CharSequence Yes or no.
        - after Remove order Reload cartActivity.
        - add putExtra for counter as quantity of product
        - Make Toast "we will ship this order after last order been shipped" if there is unshipped order.
        - after update order we have to remember orderID and add it again in the cart.
        - synchronize Address information from Prevailed online.
        - change hint Address to city
        - Modify Toast for Total Amount order when confirm order Address.

   - SettingActivity:
        - no need to change Image for Edit Information.
        - Toast fill all Information with Image.


-Admin:
    - Add msg check your information please.
    - AdminHomeActivity:
        - Add Extra Information as counter for user, product,seller and order;
        + Intent in each counter

    - approveProductActivity:
        - resize Text in head bar
        - redesign CardView
        - Remove Counter form card
        - Modify Price
        - add and corp image for product
        - after approve product reload approveProductActivity.

    - AdminMaintainProduct:
        - redesign card.
        - Add ProgressBar.Dismiss
        - put Extra parent after apply change or delete for product.
        - fix remember me check.

    - AdminNewOrdersActivity:
        - after edit product from AdminNewOrdersActivity, put extra parent and intent to AdminNewOrdersActivity.
        - Add order Id into card
        - Modify Total Amount.
        - when delete all products in order, order must be Deleted.
        - Delete intent if no.
        - Reload AdminNewOrdersActivity when choose yes for shipped.
        - delete edit from CharSequence.

- RegistrationActivity:
    - Delete Background Image