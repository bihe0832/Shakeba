<?php 

include (dirname(__FILE__).'/../module/base.class.php');
header("Content-type: text/html; charset=utf-8"); 
//提交的内容全部入库，然后跳转到初始化页面
$userInfo = array();
$userInfo["advice"] = htmlspecialchars($_POST["advice"]);
$userInfo["phone"] = htmlspecialchars($_POST["phone"]);
if($userInfo["advice"] && $userInfo["phone"]){
	$sql = "INSERT INTO `t_shakbaFeedback` ( `advice` ,`phone` ) VALUES ('".$userInfo["advice"]."', '".$userInfo["phone"]."');";
	$dbResult = BaseModule::getInstance()->execSQL($sql);
	echo "感谢您的反馈！";
}else{
	echo "参数错误！";
}
$redirecturl = "http://shakeba.bihe0832.com/m/feedback.php";
sleep(3);
echo "<script type=\"text/javascript\">window.location=\"".$redirecturl."\"</script>";
?>