<?php
$name = addslashes(strip_tags($_POST['name']));
$price = addslashes(strip_tags($_POST['price']));
$description = addslashes(strip_tags($_POST['description']));
$pid = addslashes(strip_tags($_POST['pid']));

include "connection.php";

$sql = "UPDATE `product` SET `productName` = '$name', `productDescription` = '$description', `productPrice` = $price  WHERE `product`.`productID` = $pid";
mysqli_query($con, $sql);
echo mysqli_error($con);

echo "the product has been updated";

mysqli_close($con);
?>
