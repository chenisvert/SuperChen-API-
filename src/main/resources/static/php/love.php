<?php
/*
版权信息可删除，但请勿修改
Copyright © 2020 by m@mcloc.cn
*/
$type = $_REQUEST['type'];
$love_id = $_REQUEST['id'];
include "conn.php";
$sql = "SELECT COUNT(*) FROM love_sentence";//love_sentence为数据表
$result_count = $conn->query($sql);
$love_count_arr = $result_count->fetch_all();
$love_count = $love_count_arr[0][0];

if ($type == "json") {
    if($love_id){
        $sql2 = "SELECT * FROM love_sentence WHERE id=$love_id";
        $result_byId = $conn->query($sql2);
        $result_arr_byId = mysqli_fetch_assoc($result_byId);
        $return = $result_arr_byId;
        $return = json_encode($return);
    }else{
        $index_rand = mt_rand(0, $love_count);
        $sql3 = "SELECT * FROM love_sentence WHERE id=$index_rand";
        $result_byRandId = $conn->query($sql3);
        $result_arr_byRandId = mysqli_fetch_assoc($result_byRandId);
        $return = $result_arr_byRandId;
        $return = json_encode($return);
    }
}
if ($type == "string" || $type == "") {
    if($love_id){
        $sql2 = "SELECT * FROM love_sentence WHERE id=$love_id";
        $result_byId = $conn->query($sql2);
        $result_arr_byId = mysqli_fetch_assoc($result_byId);
        $return = $result_arr_byId["data"];
    }else{
        $index_rand = mt_rand(0, $love_count);
        $sql3 = "SELECT * FROM love_sentence WHERE id=$index_rand";
        $result_byRandId = $conn->query($sql3);
        $result_arr_byRandId = mysqli_fetch_assoc($result_byRandId);
        $return = $result_arr_byRandId["data"];
    }
}

echo $return;
?>