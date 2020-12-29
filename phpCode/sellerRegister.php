<?php
$phone = addslashes(strip_tags($_POST['phone']));
$name = addslashes(strip_tags($_POST['name']));
$pass = addslashes(strip_tags($_POST['password']));
$email = addslashes(strip_tags($_POST['email']));
$address = addslashes(strip_tags($_POST['address']));



include "connection.php";
$sql = "INSERT INTO `seller`(`sellerName`, `sellerPhone`, `sellerEmail`, `sellerAddress`, `sellerPassword`)  VALUES ('$name',$phone,'$email','$address','$pass')";
mysqli_query($con,$sql) or
    die ("can't add record");

echo "Your Account has been created you can login";
   
mysqli_close($con);
?> 