<?php
/**
 * 数据库链接等系统配置信息
 * @author 子勰 code@bihe0832.com
 */

$isSAE = true;
$db_config= array();
if($isSAE){
	$db_config['host'] = SAE_MYSQL_HOST_M;
	$db_config['port'] = SAE_MYSQL_PORT;
	$db_config['user'] = SAE_MYSQL_USER;
	$db_config['passwd'] = SAE_MYSQL_PASS;
	$db_config['dbname'] = SAE_MYSQL_DB;
}else{
	$db_config['host'] = 'localhost';
	$db_config['user'] = 'root';
	$db_config['passwd'] = 'root';
	$db_config['dbname'] = 'shakeba';
}
?>
