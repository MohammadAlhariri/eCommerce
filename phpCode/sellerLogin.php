<?php



$phone = addslashes(strip_tags($_GET['email']));

$password = addslashes(strip_tags($_GET['password']));


include "connection.php";
$sql = "SELECT * FROM `seller` WHERE  `sellerEmail`='$phone'AND`sellerPassword`='$password';";
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