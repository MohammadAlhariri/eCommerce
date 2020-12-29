<?php
// Hello Form Zaid Lap...
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

$path1="https://mohammadalhariri.000webhostapp.com/MZ_eCommerce/".$path;
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