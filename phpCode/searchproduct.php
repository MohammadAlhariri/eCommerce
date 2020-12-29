<?php
include("connection.php");
if (!isset($_GET['name']))
    {die('No product name');}
    else{
        $name=$_GET['name'];

$sql = "SELECT * FROM `product`where productName like '%$name%";
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