package com.fiveonechain.digitasset.mapper;

/**
 * Created by yuanshichao on 2016/11/14.
 */
public interface UserAssetRecordMapper {
}


/*

CREATE TABLE `user_asset_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asset_id` int(11) NOT NULL,
  `from_user_id` int(11) NOT NULL,
  `operation` tinyint(4) NOT NULL,
  `old_balance` int(11) NOT NULL,
  `new_balance` int(11) NOT NULL,
  `to_user_id` int(11) NOT NULL,
  `contract_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL default CURRENT_TIMESTAMP,

  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_assetid_userid` (`asset_id`, `user_id`),

) DEFAULT CHARSET=utf8 COLLATE = utf8_bin

*/