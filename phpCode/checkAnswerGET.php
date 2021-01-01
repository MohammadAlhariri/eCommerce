<?php
$answer1 = addslashes(strip_tags($_GET['answer1']));
$answer2 = addslashes(strip_tags($_GET['answer2']));
$phone= addslashes(strip_tags($_GET['phone']));
include "connection.php";
    $sql="SELECT `userID` FROM `user` WHERE `user`.`userAnswer1`='$answer1'
    AND `user`.`userAnswer2`='$answer2' AND `user`.`userPhone`=$phone;";

if ($result = mysqli_query($con,$sql))
  {
   $emparray = array();
   while($row =mysqli_fetch_assoc($result))
       {$emparray[] = $row;}

  //echo json_encode($emparray);
  // Free result set
  mysqli_free_result($result);
  mysqli_close($con);
}