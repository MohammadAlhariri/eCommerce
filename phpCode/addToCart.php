<?php
$uid = addslashes(strip_tags($_POST['uid']));
$pid = addslashes(strip_tags($_POST['pid']));
$price = addslashes(strip_tags($_POST['price']));
$quantity = addslashes(strip_tags($_POST['quantity']));

 include("connection.php");
$sql="SELECT * FROM `order` WHERE userID=$uid ORDER BY orderID DESC LIMIT 1;";
$result=mysqli_query($con,$sql);
$row=mysqli_fetch_array($result);

if(empty($row)||$row["orderState"]=="Shipped"){
    include("connection.php");
    $addoder = "INSERT INTO `order`(`orderDate`, `userID`, `orderTotal`, `orderState`) VALUES (NOW(),$uid,500,'Not Shipped');";
    mysqli_query($con,$addoder);
    $orderid=getOrderId($uid);
        insertToOrderContent($orderid,$pid,$quantity,$price);


    echo mysqli_error($con);
}
else if(!empty($row)&&$row["orderState"]=="Not Shipped"){
    $userid=getOrderId($uid);
    insertToOrderContent($userid,$pid,$quantity,$price);
}else{
print_r($row);
}
getOrderId($uid);

function getOrderId($uid){
    include "connection.php";
    $sql1="SELECT * FROM `order` WHERE userID=$uid  AND orderState='Not Shipped';";
    $res=mysqli_query($con,$sql1);
    $r=mysqli_fetch_array($res);
    return $r["orderID"];
}

function insertToOrderContent($uid,$pid,$quantity,$price){
    include "connection.php";
    $sql2 = "INSERT INTO `order_content`(`orderID`, `productID`, `quantity`, `price`)
     VALUES ($uid,$pid,$quantity,$price);";
    mysqli_query($con,$sql2);
    echo mysqli_error($con);
    echo "Record Added";
       
}
?> 										