package com.fiveonechain.digitasset.mapper;

import com.fiveonechain.digitasset.domain.AssetRecord;
import org.apache.ibatis.annotations.*;
import org.springframework.security.access.method.P;

import java.util.List;

/**
 * Created by yuanshichao on 2016/11/14.
 */

@Mapper
public interface AssetRecordMapper {

    String INSERT_COLUMN = "asset_id, user_id, status";
    String INSERT_PROPERTY = "#{assetId}, #{userId}, #{status}";

    String ALL_COLUMN = "asset_id, create_time, user_id, status";

    @Insert("INSERT INTO asset_record (" + INSERT_COLUMN + ") VALUES (" + INSERT_PROPERTY +")")
    int insert(AssetRecord record);

    @Results(id = "asset_record", value = {
            @Result(column = "asset_id", property = "assetId"),
            @Result(column = "user_id", property = "userId"),
            @Result(column = "status", property = "status"),
            @Result(column = "create_time", property = "createTime")
    })
    @Select("SELECT " + ALL_COLUMN + " FROM asset_record WHERE asset_id = #{assetId} AND user_id = #{userId} ORDER BY id DESC")
    List<AssetRecord> selectByUserIdAndAssetId(
            @Param("userId") int userId,
            @Param("assetId") int assetId);
}


/*

CREATE TABLE `asset_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asset_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `user_id` int(11) NOT NULL,
  `status` tinyint(11) NOT NULL,
  PRIMARY KEY (`id`),
)
*/
