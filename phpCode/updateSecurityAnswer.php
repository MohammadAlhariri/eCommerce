<?php
$answer1 = addslashes(strip_tags($_POST['answer1']));
$answer2 = addslashes(strip_tags($_POST['answer2']));
$uid = addslashes(strip_tags($_POST['uid']));

include "connection.php";

$sql = "UPDATE `user` SET `userAnswer1` = '$answer1', `userAnswer2` = '$answer2' WHERE  `user`.`userID` = $uid ;";
mysqli_query($con, $sql);
echo mysqli_error($con);

echo "your Question security has been updated";

mysqli_close($con);
?>
