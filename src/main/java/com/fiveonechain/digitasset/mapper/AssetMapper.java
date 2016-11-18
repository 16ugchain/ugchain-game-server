package com.fiveonechain.digitasset.mapper;

import com.fiveonechain.digitasset.domain.Asset;
import org.apache.ibatis.annotations.*;

/**
 * Created by yuanshichao on 2016/11/14.
 */

@Mapper
public interface AssetMapper {

    String INSERT_COLUMN = "asset_id, user_id, guar_id, name, "
            + "value, description, certificate, photos, "
            + "start_time, end_time, fee, status";

    String INSERT_PROPERTY = "#{assetId}, #{userId}, #{guarId}, #{name}, "
            + "#{value}, #{description}, #{certificate}, #{photos}, "
            + "#{startTime}, #{endTime}, #{fee}, #{status}";

    String ALL_COLUMN = "asset_id, user_id, guar_id, name, "
            + "value, description, certificate, photos, "
            + "start_time, end_time, eval_conclusion, eval_value, "
            + "fee, exp_earnings, status";

    @Insert("INSERT INTO asset(" + INSERT_COLUMN + ") VALUES (" + INSERT_PROPERTY +")")
    int insert(Asset asset);

    @Results({
            @Result(property = "assetId", column = "asset_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "guarId", column = "guar_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "value", column = "value"),
            @Result(property = "description", column = "description"),
            @Result(property = "certificate", column = "certificate"),
            @Result(property = "photos", column = "photos"),
            @Result(property = "startTime", column = "start_time"),
            @Result(property = "endTime", column = "end_time"),
            @Result(property = "evalConclusion", column = "eval_conclusion"),
            @Result(property = "evalValue", column = "eval_value"),
            @Result(property = "fee", column = "fee"),
            @Result(property = "expEarnings", column = "exp_earnings"),
            @Result(property = "status", column = "status"),
    })
    @Select("SELECT " + ALL_COLUMN + " FROM asset WHERE asset_id = #{assetId}")
    Asset select(@Param("assetId") int assetId);


    @Select("SELECT status FROM asset WHERE asset_id = #{assetId} FOR UPDATE")
    Integer selectStatusForUpdate(@Param("assetId") int assetId);

    @Update("UPDATE asset SET status = #{status} WHERE asset_id = #{assetId}")
    int updateStatusByAssetId(@Param("status") int status, @Param("assetId") int assetId);

    @Update("UPDATE asset SET eval_conclusion = #{evalConclusion}, eval_value = #{evalValue}, fee = #{fee}, exp_earnings = #{expEarnings} WHERE asset_id = #{assetId}")
    int updateAssetEvalInfoByAssetId(Asset asset);
}

/*

CREATE TABLE `asset` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asset_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `guar_id` int(11) NOT NULL,

  `name` varchar(20) CHARACTER SET utf8 NOT NULL,
  `value` int(11) NOT NULL ,
  `description` varchar(200) CHARACTER SET utf8 NOT NULL,
  `certificate` varchar(32) CHARACTER SET latin1 NOT NULL,
  `photos` varchar(192) CHARACTER SET latin1 NOT NULL ,

  `start_time` timestamp NOT NULL,
  `end_time` timestamp NOT NULL,

  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  `eval_conclusion` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `eval_value` int(11) NOT NULL DEFAULT -1,
  `fee` int(11) NOT NULL DEFAULT -1,
  `exp_earnings` int(11) NOT NULL DEFAULT -1,

  `status` tinyint(4) NOT NULL ,

  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_assetid` (`asset_id`)

)

*/
