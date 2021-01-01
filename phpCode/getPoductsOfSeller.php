<?php
include("connection.php");
if (!isset($_GET['sid']))
    {die('No product id');}
    else{
$sid = strip_tags(addslashes($_GET['sid']));


$sql = "SELECT * FROM `product`where sellerID = $sid";
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
    }
