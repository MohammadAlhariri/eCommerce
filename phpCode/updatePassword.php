<?php
$password = addslashes(strip_tags($_POST['password']));

$uid = addslashes(strip_tags($_POST['uid']));

include "connection.php";

$sql = "UPDATE `user` SET `userPassword` = '$password' WHERE  `user`.`userID` = $uid ;";
mysqli_query($con, $sql);
echo mysqli_error($con);

echo "Password has been change you can now login";

mysqli_close($con);
?>
