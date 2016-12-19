package com.fiveonechain.digitasset.mapper;

import com.fiveonechain.digitasset.domain.AssetOrder;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by yuanshichao on 2016/11/14.
 */
@Mapper
public interface AssetOrderMapper {
    final static String columns = "asset_id,order_id,user_id,amount,unit_prices,buyer_id,end_time,status";
    final static String all_columns = "asset_id,order_id,user_id,amount,unit_prices,buyer_id,status,create_time,update_time,end_time";
    final static String entity = "#{assetOrder.assetId},#{assetOrder.orderId},#{assetOrder.userId}," +
            "#{assetOrder.amount},#{assetOrder.unitPrices},#{assetOrder.buyerId},#{assetOrder.endTime},#{assetOrder.status}";

    @Insert("insert into asset_order (" + columns + ") values(" + entity + ")")
    int insertOrder(@Param("assetOrder") AssetOrder assetOrder);

    @Results(id = "assetOrder", value = {
            @Result(property = "assetId", column = "asset_id"),
            @Result(property = "orderId", column = "order_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "unitPrices", column = "unit_prices"),
            @Result(property = "buyerId", column = "buyer_id"),
            @Result(property = "endTime", column = "end_time")
    })
    @Select("select " + all_columns + " from asset_order where order_id=#{orderId}")
    AssetOrder findByOrderId(@Param("orderId") int orderId);

    @ResultMap("assetOrder")
    @Select("select " + all_columns + " from asset_order where user_id=#{userId}")
    List<AssetOrder> findListByUsreId(@Param("userId") int userId);

    @ResultMap("assetOrder")
    @Select("select " + all_columns + " from asset_order where buyer_id=#{userId}")
    List<AssetOrder> getAssetOrderListByBuyerId(@Param("userId") int userId);

    @ResultMap("assetOrder")
    @Select("select " + all_columns + " from asset_order where asset_id=#{assetId}")
    List<AssetOrder> findListByAssetId(@Param("assetId") int assetId);

    @ResultMap("assetOrder")
    @Select("select " + all_columns + " from asset_order where status=#{status}")
    List<AssetOrder> findListByStatus(@Param("status") int status);

    @Select("SELECT status FROM asset_order WHERE order_id = #{orderId} FOR UPDATE")
    Integer selectStatusForUpdate(@Param("orderId") int orderId);

    @Update("UPDATE asset_order SET status = #{status} WHERE order_id = #{orderId}")
    int updateStatusByOrderId(@Param("status") int status, @Param("orderId") int orderId);
}


/*

CREATE TABLE `asset_order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `order_id` int(11) NOT NULL,
  `asset_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `amount` int(11) NOT NULL,
  `buyer_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `end_time` timestamp NOT NULL,
  `update_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL default '0',

  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_assetid_userid` (`asset_id`, `user_id`)

) DEFAULT CHARSET=utf8 COLLATE = utf8_bin

*/