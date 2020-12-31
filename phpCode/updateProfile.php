<?php
$name = addslashes(strip_tags($_POST['name']));
$phone = addslashes(strip_tags($_POST['phone']));
if (!empty($_POST['image'])) {
    $image = addslashes(strip_tags($_POST['image']));
} else {
    $image = null;
}

$address = addslashes(strip_tags($_POST['address']));
$uid = addslashes(strip_tags($_POST['uid']));

include "connection.php";

$sql = "UPDATE `user` SET `userName` = '$name', `userPhone` = '$phone', `userAddress` = '$address', `userImage` = '$image' WHERE `user`.`userID` = $uid";
mysqli_query($con, $sql);
echo mysqli_error($con);

echo "your profile has been updated";

mysqli_close($con);
?>
