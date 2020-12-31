<?php



$phone = addslashes(strip_tags($_GET['phone']));

$password = addslashes(strip_tags($_GET['password']));
$parent=addslashes(strip_tags($_GET['parent']));


include "connection.php";
$sql = "SELECT `userID`, `userName`, `userPhone`, `userEmail`, `userAddress`, `userPassword`, `userImage`, `userAnswer1`, `userAnswer2`,`parent`  
    FROM `user` WHERE `userPhone`='$phone'AND`userPassword`='$password'AND `parent` ='$parent';";
if ($result = mysqli_query($con,$sql))
  {
   $emparray = array();
   while($row =mysqli_fetch_assoc($result))
       
           {$emparray[] = $row;}

  echo json_encode($emparray);
  // Free result set
 
  mysqli_free_result($result);
  mysqli_close($con);
}
?> 