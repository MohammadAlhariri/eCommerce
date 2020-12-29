<?php
$cid = addslashes(strip_tags($_GET['phone']));
$name = addslashes(strip_tags($GET['name']));
$key = addslashes(strip_tags($_GET['password']));



include "connection.php";
$sql = "INSERT INTO `user`( `userName`, `userPhone`, `userEmail`) VALUES ('$name',$cid,'$key')";
mysqli_query($con,$sql) or
    die ("can't add record");

echo "Your Account has been created you can login";
   
mysqli_close($con);
?> 