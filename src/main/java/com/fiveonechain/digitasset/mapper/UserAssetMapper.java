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

    @Results({
            @Result(property = "assetId", column = "asset_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "contractId", column = "contract_id"),
            @Result(property = "balance", column = "balance"),
            @Result(property = "tradeBalance", column = "trade_balance"),
            @Result(property = "lockBalance", column = "lock_balance"),
    })
    @Select("SELECT " + ALL_COLUMN + " FROM asset WHERE asset_id = #{assetId} AND user_id = #{userId}")
    UserAsset select(@Param("assetId") int assetId, @Param("userId") int userId);

    @Results({
            @Result(property = "assetId", column = "asset_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "contractId", column = "contract_id"),
            @Result(property = "balance", column = "balance"),
            @Result(property = "tradeBalance", column = "trade_balance"),
            @Result(property = "lockBalance", column = "lock_balance"),
    })
    @Select("SELECT " + ALL_COLUMN + " FROM asset WHERE asset_id = #{assetId} AND user_id = #{userId} FOR UPDATE")
    UserAsset selectForUpdate(@Param("assetId") int assetId, @Param("userId") int userId);

    @Results({
            @Result(property = "assetId", column = "asset_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "contractId", column = "contract_id"),
            @Result(property = "balance", column = "balance"),
            @Result(property = "tradeBalance", column = "trade_balance"),
            @Result(property = "lockBalance", column = "lock_balance"),
    })
    @Select("SELECT " + ALL_COLUMN + " FROM asset WHERE AND user_id = #{userId}")
    List<UserAsset> selectByUserId(@Param("userId") int userId);


    @Results({
            @Result(property = "assetId", column = "asset_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "contractId", column = "contract_id"),
            @Result(property = "balance", column = "balance"),
            @Result(property = "tradeBalance", column = "trade_balance"),
            @Result(property = "lockBalance", column = "lock_balance"),
    })
    @Select("SELECT " + ALL_COLUMN + " FROM asset WHERE AND asset_id = #{assetId}")
    List<UserAsset> selectByAssetId(@Param("assetId") int assetId);

    @Update("UPDATE asset SET trade_balance = trade_balance - #{amount}, lock_balance = lock_balance + #{amount} "
            + " WHERE asset_id = #{assetId} AND user_id = #{userId}")
    int lockTradeBalance(
            @Param("amount") int amount,
            @Param("assetId") int assetId,
            @Param("userId") int userId);

    @Update("UPDATE asset SET trade_balance = trade_balance + #{amount}, lock_balance = lock_balance - #{amount} "
            + " WHERE asset_id = #{assetId} AND user_id = #{userId}")
    int unLockTradeBalance(
            @Param("amount") int amount,
            @Param("assetId") int assetId,
            @Param("userId") int userId);

    @Update("UPDATE asset SET trade_balance = #{tradeBalance} WHERE asset_id = #{assetId} AND user_id = #{userId}")
    int updateTradeBalance(
            @Param("tradeBalance") int tradeBalance,
            @Param("assetId") int assetId,
            @Param("userId") int userId);

    @Update("UPDATE asset SET balance = balance - #{amount}, lock_balance = lock_balance - #{amount}, contract_id = #{contractId} "
            + " WHERE asset_id = #{assetId} AND user_id = #{userId}")
    int withdrawBalance(
            @Param("amount") int amount,
            @Param("assetId") int assetId,
            @Param("userId") int userId,
            @Param("contractId") int contractId);

    @Update("UPDATE asset SET balance = balance + #{amount}, contract_id = #{contractId} "
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
