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
    final static String entity = "#{assetOrder.asset_id},#{assetOrder.order_id},#{assetOrder.user_id}," +
            "#{assetOrder.amount},#{assetOrder.unit_prices},#{assetOrder.buyer_id},#{assetOrder.end_time},#{assetOrder.status}";
    @Insert("insert into asset_order ("+columns+") values("+entity+")")
    int insertOrder(@Param("assetOrder") AssetOrder assetOrder);

    @Select("select "+all_columns+" from asset_order where order_id=#{order_id}")
    AssetOrder findByOrderId(@Param("order_id")int order_id);

    @Select("select "+all_columns+" from asset_order where user_id=#{user_id}")
    List<AssetOrder> findListByUsreId(@Param("user_id")int user_id);

    @Select("select "+all_columns+" from asset_order where asset_id=#{asset_id}")
    List<AssetOrder> findListByAssetId(@Param("asset_id")int asset_id);

    @Select("select "+all_columns+" from asset_order where status=#{status}")
    List<AssetOrder> findListByStatus(@Param("status")int status);

    @Select("SELECT status FROM asset_order WHERE order_id = #{order_id} FOR UPDATE")
    Integer selectStatusForUpdate(@Param("order_id") int order_id);

    @Update("UPDATE asset_order SET status = #{status} WHERE order_id = #{order_id}")
    int updateStatusByOrderId(@Param("status") int status, @Param("order_id") int order_id);
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