<?php
$name = addslashes(strip_tags($_POST['name']));
$phone = addslashes(strip_tags($_POST['phone']));

  



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
$address = addslashes(strip_tags($_POST['address']));
$uid = addslashes(strip_tags($_POST['uid']));

include "connection.php";

$sql = "UPDATE `user` SET `userName` = '$name', `userPhone` = $phone, `userAddress` = '$address', `userImage` = '$path1' WHERE `user`.`userID` = $uid";
mysqli_query($con, $sql);
echo mysqli_error($con);

echo "your profile has been updated";

mysqli_close($con);
?>
