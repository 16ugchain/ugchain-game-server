package com.fiveonechain.digitasset.mapper;

/**
 * Created by yuanshichao on 2016/11/14.
 */
public interface AssetOrderRecordMapper {
}


/*

CREATE TABLE `asset_order_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `operation` tinyint(4) NOT NULL,
  `old_status` tinyint(4) NOT NULL,
  `new_status` tinyint(4) NOT NULL,


  PRIMARY KEY (`id`)

) DEFAULT CHARSET=utf8 COLLATE = utf8_bin

*/