<?php
$conn = new mysqli("localhost","root","root","love");//数据库信息
if($conn->connect_error){
    die("数据连接失败");
}
?>