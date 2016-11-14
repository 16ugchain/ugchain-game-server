package com.fiveonechain.digitasset.mapper;

/**
 * Created by yuanshichao on 2016/11/14.
 */
public interface AssetMapper {
}

/*

CREATE TABLE `asset` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asset_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `corp_id` int(11) NOT NULL,
  `name` varchar(5) NOT NULL,
  `value` int(11) NOT NULL,
  `share` int(11) NOT NULL,
  `create_time` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `update_time ` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL default '0',

  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_assetid` (`asset_id`),

) DEFAULT CHARSET=utf8 COLLATE = utf8_bin

*/
