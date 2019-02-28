
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=0">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="format-detection" content="telephone=no">
    <link rel = "Shortcut Icon" href="./css/shakeba_32.ico" />
    <meta name="author" CONTENT="子勰(bihe0832), code@bihe0832.com" />
    <meta name="keywords" content="摇吧" />
    <meta name="description" content="摇吧是子勰开发的一款基于手机摇一摇功能的小游戏或者游戏助手集合。目前摇吧已经支持骰子战争。摇吧中，所有游戏的使用方法都是摇一摇，打开手机摇一摇，体验摇吧带来的乐趣吧！" />

  <title>摇吧建议反馈</title>

  <link rel="stylesheet" type="text/css" href="./css/global.css" />
  <style type="text/css">

    body { 
	font-family: "Microsoft Yahei"; 
	font-size: 16px; 
	margin: 10px; 
	background:#f2f2f2;
	background-repeat: no-repeat;
	background-position:center;
	background-attachment:fixed;
} 
input,button,select,textarea{ outline:none;}
.vote{ 
	margin:0 auto;
	font-size: 16px; 
	padding:20px;
	color:#000;
	text-align:center;
}
#title{ 
	text-align:center;
	font-weight: bold; 
	line-height: 50px;
	border-bottom:1px solid #ccc;
	margin-bottom: 15px;

}
.center{ 
	text-align:center;
}

li{
	padding-top: 3px;
	font-weight: bold; 
	text-align:left;
	line-height: 40px;
}

li.last{
	text-align:center;
	font-weight: bold; 
}
.butt{
	background-color: #333;
    border-color: #eee;
	color: #f2f2f2;
	-moz-user-select: none;
    border: 1px solid transparent;
    border-radius: 4px 4px 4px 4px;
    cursor: pointer;
    display: inline-block;
    font-size: 16px;
    font-weight: normal;
    line-height: 1.42857;
    margin-bottom: 0;
    margin-top: 15px;
    padding: 10px 25px;
    text-align: center;
    vertical-align: middle;
    white-space: nowrap;
	text-decoration: none;
}

#advice{
	width:85%;
	margin-top: 15px;
}
.numInfo{
	padding-left: 3px;
	margin-top: 15px;
	margin-bottom: 15px;
	width:150px;
	height:25px;
	font-family: "Microsoft Yahei"; 
	font-weight: bold; 
}
  </style>
</head>
<body>
<?php 
	$openID = $_GET["openID"]
?>
<form action="feedback_result.php" method="post" id="userInfo">
<div class="vote">
		<div id="title">感谢您反馈摇吧的使用意见</div>
		<input class="vote_list" style="display:none;" name="openID" id="openID" value=""  />
		<ul>		
		<li class="center">您的建议内容</li>	
			<li  class="center">
			<textarea rows="8"name="advice" id="advice" style= "resize:none;"/></textarea>
			</li>
			
			<li class="last">
				您的联系方式：<input class="numInfo" type="text" name="phone" id="phone"/>
			</li>
		</ul>
		<p>
			<a id="butt" class="butt" href="#" onClick="javascript:sub();">点击提交信息</a>
		</p>
</div>
</form>

<script type="text/javascript">
function sub(){
	document.getElementById("userInfo").submit();
}
</script>


<script type="text/javascript" src="https://tajs.qq.com/stats?sId=25799863" charset="UTF-8"></script>
</body>
</html>