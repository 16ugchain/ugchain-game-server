package com.fiveonechain.digitasset.mapper;

import com.fiveonechain.digitasset.domain.AssetRecord;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by yuanshichao on 2016/11/14.
 */

@Mapper
public interface AssetRecordMapper {

    String INSERT_COLUMN = "asset_id, user_id, status";
    String INSERT_PROPERTY = "#{assetId}, #{userId}, #{status}";

    @Insert("INSERT INTO asset_record (" + INSERT_COLUMN + ") VALUES (" + INSERT_PROPERTY +")")
    int insert(AssetRecord record);
}


/*

CREATE TABLE `asset_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asset_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(11) NOT NULL,
  `status` tinyint(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_assetid` (`asset_id`)
)
*/
