package com.ugc.micropayment.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * Created by fanjl on 2017/4/5.
 */
@Mapper
public interface AccountMapper {
}
/*

CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` int(11) NOT NULL,
  `address` varchar(42) NOT NULL,
  `nonce` int(11) NOT NULL,
  `amount` decimal(10,5)  NOT NULL,
  `create_time` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL default '0',
  PRIMARY KEY (`id`),
) DEFAULT CHARSET=utf8 COLLATE = utf8_bin

*/
