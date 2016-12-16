package com.fiveonechain.digitasset.mapper;

import com.fiveonechain.digitasset.domain.Asset;
import com.fiveonechain.digitasset.mapper.util.SimpleSelectInExtendedLanguageDriver;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by yuanshichao on 2016/11/14.
 */

@Mapper
public interface AssetMapper {

    String INSERT_COLUMN = "asset_id, user_id, guar_id, name, "
            + "value, eval_value, description, certificate, photos, cycle, "
            + "fee, status";

    String INSERT_PROPERTY = "#{assetId}, #{userId}, #{guarId}, #{name}, "
            + "#{value}, #{evalValue}, #{description}, #{certificate}, #{photos}, #{cycle}, "
            + "#{fee}, #{status}";

    String ALL_COLUMN = "asset_id, user_id, guar_id, name, "
            + "value, description, certificate, photos, cycle, "
            + "start_time, end_time, eval_conclusion, eval_value, "
            + "fee, exp_earnings, status, create_time, update_time";

    @Insert("INSERT INTO asset(" + INSERT_COLUMN + ") VALUES (" + INSERT_PROPERTY +")")
    int insert(Asset asset);

    @Results(id = "asset", value = {
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
            @Result(property = "cycle", column = "cycle"),
    })
    @Select("SELECT " + ALL_COLUMN + " FROM asset WHERE asset_id = #{assetId}")
    Asset select(@Param("assetId") int assetId);

    @ResultMap("asset")
    @Select("SELECT " + ALL_COLUMN + " FROM asset WHERE status = #{status} ORDER BY asset_id DESC")
    List<Asset> selectByStatus(@Param("status") int status);

    @ResultMap("asset")
    @Select("SELECT " + ALL_COLUMN + " FROM asset WHERE guar_id = #{guarId} AND status = #{status} ORDER BY asset_id DESC")
    List<Asset> selectByGuarIdAndStatus(
            @Param("guarId") int guarId,
            @Param("status") int status);

    @ResultMap("asset")
    @Select("SELECT " + ALL_COLUMN + " FROM asset WHERE user_id = #{userId} AND status = #{status} ORDER BY asset_id DESC")
    List<Asset> selectByUserIdAndStatus(
            @Param("userId") int userId,
            @Param("status") int status);

    @ResultMap("asset")
<<<<<<< HEAD
    @Select("SELECT " + ALL_COLUMN + " FROM asset WHERE user_id = #{userId}  ORDER BY asset_id DESC")
    List<Asset> selectByUserId(
            @Param("userId") int userId);

=======
    @Select("SELECT " + ALL_COLUMN + " FROM asset WHERE user_id = #{userId} ORDER BY asset_id DESC")
    List<Asset> selectByUserId(
            @Param("userId") int userId);
>>>>>>> e9ea49bf245850e141257f7a361ff3b7c809bd44

    @ResultMap("asset")
    @Select("SELECT " + ALL_COLUMN + " FROM asset WHERE guar_id = #{guarId} AND status != #{status} ORDER BY update_time DESC")
    List<Asset> selectByGuarIdAndNotStatus(
            @Param("guarId") int guarId,
            @Param("status") int notStatus);

    @Lang(SimpleSelectInExtendedLanguageDriver.class)
    @Select("SELECT " + ALL_COLUMN + " FROM asset WHERE guar_id = #{guarId} AND status IN (#{statusList}) ORDER BY update_time DESC")
    List<Asset> selectByGuarIdAndStatusList(
            @Param("guarId") int guarId,
            @Param("statusList") List<Integer> statusList);

    @Lang(SimpleSelectInExtendedLanguageDriver.class)
    @Select("SELECT " + ALL_COLUMN + " FROM asset WHERE user_id = #{userId} AND status IN (#{statusList}) ORDER BY update_time DESC")
    List<Asset> selectByGuarIdAndUserIdAndStatusList(
            @Param("guarId") int guarId,
            @Param("userId") int userId,
            @Param("statusList") List<Integer> statusList);

    @Select("SELECT status FROM asset WHERE asset_id = #{assetId} FOR UPDATE")
    Integer selectStatusForUpdate(@Param("assetId") int assetId);

    @Update("UPDATE asset SET status = #{status} WHERE asset_id = #{assetId}")
    int updateStatusByAssetId(@Param("status") int status, @Param("assetId") int assetId);

    @Update("UPDATE asset SET eval_conclusion = #{evalConclusion}, eval_value = #{evalValue}, fee = #{fee}, exp_earnings = #{expEarnings} WHERE asset_id = #{assetId}")
    int updateAssetEvalInfoByAssetId(Asset asset);

    @Update("UPDATE asset SET start_time = #{startTime}, end_time = #{endTime} WHERE asset_id = #{assetId}")
    int updateAssetIssueInfoByAssetId(Asset asset);
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

  `cycle` int(11) NOT NULL,
  `start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',

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
