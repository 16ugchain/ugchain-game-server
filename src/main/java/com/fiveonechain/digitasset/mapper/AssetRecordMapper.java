package com.fiveonechain.digitasset.mapper;

/**
 * Created by yuanshichao on 2016/11/14.
 */
public interface AssetRecordMapper {
}


/*

CREATE TABLE `asset_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asset_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `operation` tinyint(4) NOT NULL,
  `old_status` tinyint(4) NOT NULL,
  `new_status` tinyint(4) NOT NULL,

  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_assetid` (`asset_id`)

) DEFAULT CHARSET=utf8 COLLATE = utf8_bin

*/