TODO list

- General Design:
    - Prime Color  ->Done
    - Dark Prime Color ->Done
    - App_logo  ->Done
    - Add Icon

- Seller
    - Seller LoginActivity:
    - Modify Toast. 
       
    - Seller AddProduct:
        - change to RelativeLayout MISSED
        - corp for imageview in add product DONE

    - Seller HomeActivity:
        - CardView
            - redesign for CardView. DONE
            - corp for Imageview. DONE
            - Price change Equal sign. DONE

    - Seller LogoutActivity:
        Add Toast When User Logout. DONE.

    - In Database Seller:
        - Make phone number and Email unique key. DONE


User:

    - User Registration activity :
        - Modify Toast. Login, cartActivity "Remove Error", DONE
        - Add Toast For Wrong Username or Password  DONE.
        + when press back forward activity to HomeActivity. DONE.   
        - Delete comment from the API. inprogress.

   - HomeActivity:
        + reload HomeActivity every X time.

   - ProductDetailsActivity:
        - corp Imageview DONE 
        - Add TextView as counter for Total Price. DONE

    - cartActivity:
        - Remove Order add CharSequence Yes or no. done 
        - after Remove order Reload cartActivity. done 
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