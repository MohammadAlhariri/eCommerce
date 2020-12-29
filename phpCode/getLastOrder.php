<?php
$uid = addslashes(strip_tags($_GET['uid']));
include "connection.php";
    $sql="SELECT * FROM `order` WHERE userID=$uid ORDER BY orderID DESC LIMIT 1;";

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