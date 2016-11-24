package com.fiveonechain.digitasset.mapper;

import com.fiveonechain.digitasset.domain.UserAssetRecord;
import org.apache.ibatis.annotations.Insert;

/**
 * Created by yuanshichao on 2016/11/14.
 */
public interface UserAssetRecordMapper {


    @Insert("INSERT INTO user_asset_record ")
    int insert(UserAssetRecord record);

}


/*

CREATE TABLE `user_asset_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asset_id` int(11) NOT NULL,
  `from_user_id` int(11) NOT NULL,
  `to_user_id` int(11) NOT NULL,
  `operation` tinyint(4) NOT NULL,
  `amount` int(11) NOT NULL,
  `contract_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL default CURRENT_TIMESTAMP,

  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_assetid_userid` (`asset_id`, `from_user_id`)

)

*/