<?php
/**
 * 网站配置的基本信息
 * @author 子勰 code@bihe0832.com
 */
 error_reporting(true);
 //报告运行时错误
 error_reporting(E_ERROR);
 //报告所有错误
 error_reporting(E_ALL);

require_once dirname(__FILE__).'/conn.php';
require_once dirname(__FILE__).'/../tools/tools.php';
define("SITE_NAME", "摇吧，来到摇吧，你就摇吧！");
define("APP_VERSION_CODE", 2);
define("APP_VERSION_NAME", "1.1.0");
define("APP_DOWNLOAD_URL", "http://microdemo.bihe0832.com/MultiQrcode/Demo2/Gen_Signature_Android.apk");
define("SITE_VERSION","V1.0.15113001");
define("COPY_RIGHT", "COPYRIGHT &copy; ZIXIE ".SITE_VERSION." ALL RIGHTS RESERVED");
define("COPY_RIGHT_SHORT", "COPYRIGHT &copy; ZIXIE ".SITE_VERSION);
if($isSAE){
	define("SOURCE_LINK", "http://shakeba.bihe0832.com");
}else{
	define("SOURCE_LINK", "http://localhost");
}
