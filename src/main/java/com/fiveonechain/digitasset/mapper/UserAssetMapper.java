package com.fiveonechain.digitasset.mapper;

import com.fiveonechain.digitasset.domain.UserAsset;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by yuanshichao on 2016/11/14.
 */

@Mapper
public interface UserAssetMapper {

    String INSERT_COLUMN = "asset_id, user_id, contract_id, "
            + "balance, trade_balance, lock_balance";

    String INSERT_PROPERTY = "#{assetId}, #{userId}, #{contractId}, "
            + "#{balance}, #{tradeBalance}, #{lockBalance} ";

    String ALL_COLUMN = "asset_id, user_id, contract_id, "
            + "balance, trade_balance, lock_balance ";

    @Insert("INSERT INTO user_asset(" + INSERT_COLUMN + ") VALUES (" + INSERT_PROPERTY +")")
    int insert(UserAsset userAsset);

    @Results(id = "userasset", value = {
            @Result(property = "assetId", column = "asset_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "contractId", column = "contract_id"),
            @Result(property = "balance", column = "balance"),
            @Result(property = "tradeBalance", column = "trade_balance"),
            @Result(property = "lockBalance", column = "lock_balance"),
    })
    @Select("SELECT " + ALL_COLUMN + " FROM user_asset WHERE asset_id = #{assetId} AND user_id = #{userId}")
    UserAsset select(@Param("assetId") int assetId, @Param("userId") int userId);

    @ResultMap("userasset")
    @Select("SELECT " + ALL_COLUMN + " FROM user_asset WHERE asset_id = #{assetId} AND user_id = #{userId} FOR UPDATE")
    UserAsset selectForUpdate(@Param("assetId") int assetId, @Param("userId") int userId);

    @ResultMap("userasset")
    @Select("SELECT " + ALL_COLUMN + " FROM user_asset WHERE user_id = #{userId}")
    List<UserAsset> selectByUserId(@Param("userId") int userId);

    @ResultMap("userasset")
    @Select("SELECT " + ALL_COLUMN + " FROM user_asset WHERE asset_id = #{assetId} AND balance = #{balance}")
    UserAsset selectByAssetIdAndBalance(
            @Param("assetId") int assetId,
            @Param("balance") int balance);

    @ResultMap("userasset")
    @Select("SELECT " + ALL_COLUMN + " FROM user_asset WHERE asset_id = #{assetId} AND trade_balance > 0 ORDER BY trade_balance DESC LIMIT #{start},#{limit}")
    List<UserAsset> selectAvailByAssetIdOrderByTradePages(
            @Param("assetId") int assetId,
            @Param("start") int start,
            @Param("limit") int limit);

    @ResultMap("userasset")
    @Select("SELECT " + ALL_COLUMN + " FROM user_asset WHERE asset_id = #{assetId} AND trade_balance > 0 ORDER BY trade_balance DESC")
    List<UserAsset> selectAvailByAssetIdOrderByTrade(
            @Param("assetId") int assetId);

    @Select("SELECT SUM(trade_balance) FROM user_asset WHERE asset_id = #{assetId}")
    Integer sumTradeBalanceByAssetId(
            @Param("assetId") int assetId);

    @Select("SELECT count(1) FROM user_asset WHERE asset_id = #{assetId} AND trade_balance > 0")
    int countAvailByAssetId(@Param("assetId") int assetId);

    @Results({
            @Result(property = "assetId", column = "asset_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "contractId", column = "contract_id"),
            @Result(property = "balance", column = "balance"),
            @Result(property = "tradeBalance", column = "trade_balance"),
            @Result(property = "lockBalance", column = "lock_balance"),
    })
    @Select("SELECT " + ALL_COLUMN + " FROM user_asset WHERE AND asset_id = #{assetId}")
    List<UserAsset> selectByAssetId(@Param("assetId") int assetId);

    @Update("UPDATE user_asset SET trade_balance = trade_balance - #{amount}, lock_balance = lock_balance + #{amount} "
            + " WHERE asset_id = #{assetId} AND user_id = #{userId}")
    int lockTradeBalance(
            @Param("amount") int amount,
            @Param("assetId") int assetId,
            @Param("userId") int userId);

    @Update("UPDATE user_asset SET trade_balance = trade_balance + #{amount}, lock_balance = lock_balance - #{amount} "
            + " WHERE asset_id = #{assetId} AND user_id = #{userId}")
    int unLockTradeBalance(
            @Param("amount") int amount,
            @Param("assetId") int assetId,
            @Param("userId") int userId);

    @Update("UPDATE user_asset SET trade_balance = #{tradeBalance} WHERE asset_id = #{assetId} AND user_id = #{userId}")
    int updateTradeBalance(
            @Param("tradeBalance") int tradeBalance,
            @Param("assetId") int assetId,
            @Param("userId") int userId);

    @Update("UPDATE user_asset SET balance = balance - #{amount}, lock_balance = lock_balance - #{amount}, contract_id = #{contractId} "
            + " WHERE asset_id = #{assetId} AND user_id = #{userId}")
    int withdrawBalance(
            @Param("amount") int amount,
            @Param("assetId") int assetId,
            @Param("userId") int userId,
            @Param("contractId") int contractId);

    @Update("UPDATE user_asset SET balance = balance + #{amount}, contract_id = #{contractId} "
            + " WHERE asset_id = #{assetId} AND user_id = #{userId}")
    int depositBalance(
            @Param("amount") int amount,
            @Param("assetId") int assetId,
            @Param("userId") int userId,
            @Param("contractId") int contractId);
}

/*

CREATE TABLE `user_asset` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asset_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `contract_id` int(11) NOT NULL,
  `balance` int(11) NOT NULL,
  `trade_balance` int(11)  NOT NULL,
  `lock_balance` int(11) NOT NULL,
  `create_time` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,

  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_assetid_userid` (`asset_id`, `user_id`)

)

*/
