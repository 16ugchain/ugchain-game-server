package com.fiveonechain.digitasset.mapper;

/**
 * Created by yuanshichao on 2016/11/14.
 */
public interface ContractMapper {
}

/*

CREATE TABLE `contract` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `contract_id` int(11) NOT NULL,
  `create_time` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `content` blob NOT NULL,

  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_assetid` (`contract_id`)

) DEFAULT CHARSET=utf8 COLLATE = utf8_bin

*/
