<?php
    $name = addslashes(strip_tags($_POST['name']));
    $des = addslashes(strip_tags($_POST['description']));
    $price = addslashes(strip_tags($_POST['price']));
    $category = addslashes(strip_tags($_POST['category']));
    $sid = addslashes(strip_tags($_POST['sellerID']));
    $image = addslashes(strip_tags($_POST['image']));

    $image_no=date("Y&m&d&h&i&s");//or Anything You Need
    $image = $_POST['image'];
    $path = "uploads/".$image_no.".jpg";

    $status = file_put_contents($path,base64_decode($image));
    if($status){
     echo "Successfully Uploaded";
    }else{
     echo "Upload failed";
    }

    $path1="https://ecommerceliu.000webhostapp.com/eCommerceLIU/".$path;
    $state="Not Validate";
    $date=getdate();



include "connection.php";

$sql = "INSERT INTO `product`(`productName`, `productDescription`, `productPrice`, `productImage`, `productCategory`, `productState`, `productDate`, `sellerID`) 
VALUES ('$name','$des',$price,'$path1','$category','$state',NOW(),$sid)";
mysqli_query($con,$sql);
echo mysqli_error($con);

echo "Record Added";
   
mysqli_close($con);
?> 					
<!-- SELECT `product`.`productID`,`product`.`productName`,`product`.`productPrice`,`order`.`orderTotal`,`order_content`.`quantity`,`order_content`.`price` FROM `product`,`order`,`order_content` WHERE `product`.`productID`=`order_content`.`productID`AND `order`.`orderID`=order_content.orderID AND `order`.`userID`=3 -->
<!-- SELECT `product`.`productID`,`product`.`productName`,`product`.`productPrice`,`order`.`userID`,`order`.`orderTotal`,`order_content`.`quantity`,`order_content`.`price` FROM `product`,`order`,`order_content` WHERE `product`.`productID`=`order_content`.`productID`AND `order`.`orderID`=order_content.orderID -->
<!-- UPDATE `order` SET `orderTotal` = '44', `customerName` = '10', `customerAddress` = '10', `customerPhone` = '444', `customerCity` = '444' WHERE `order`.`orderID` = 28 -->
