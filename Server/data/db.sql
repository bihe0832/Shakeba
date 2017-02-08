DROP TABLE IF EXISTS `t_shakbaFeedback`;
CREATE TABLE `t_shakbaFeedback` (
  `id` bigint unsigned NOT NULL auto_increment,
  `advice` varchar(1024) NOT NULL default '',
  `phone` varchar(1024) NOT NULL default '',
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8;
