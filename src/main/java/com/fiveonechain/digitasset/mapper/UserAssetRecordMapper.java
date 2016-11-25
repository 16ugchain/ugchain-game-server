package com.fiveonechain.digitasset.mapper;

import com.fiveonechain.digitasset.domain.UserAssetRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by yuanshichao on 2016/11/14.
 */
@Mapper
public interface UserAssetRecordMapper {

    String INSERT_COLUMN = "asset_id, user_id, peer_id, operation, amount, contract_id";
    String INSERT_PROPERTY = "#{assetId}, #{userId}, #{peerId}, #{operation}, #{amount}, #{contractId}";

    @Insert("INSERT INTO user_asset_record (" + INSERT_COLUMN + ") VALUES (" + INSERT_PROPERTY +")")
    int insert(UserAssetRecord record);

}


/*

CREATE TABLE `user_asset_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asset_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `peer_id` int(11) NOT NULL,
  `operation` tinyint(4) NOT NULL,
  `amount` int(11) NOT NULL,
  `contract_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL default CURRENT_TIMESTAMP,

  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_assetid_userid` (`asset_id`, `user_id`)

)

*/