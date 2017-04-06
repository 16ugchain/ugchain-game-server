package com.fiveonechain.digitasset.mapper;

/**
 * Created by fanjl on 2017/4/6.
 */
public interface InnerRecordMapper {
}

/*
内部转账表
CREATE TABLE `inner_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `inner_record_id` int(11) NOT NULL ,
  `from_address` varchar(50) NOT NULL,
  `to_address` varchar(50) NOT NULL,
  `amount` decimal(10,5) NOT NULL,
  `nonce` int(11) NOT NULL,
  `create_time` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL default '0',
  PRIMARY KEY (`id`),
) DEFAULT CHARSET=utf8 COLLATE = utf8_bin
//转账前查询账户是否存在。查询amount是否足够。
//防止双花，转账前查询nonce和当前nonce是否一致，交易后nonce+1.
//交易时使用事物以及行级锁，transactional，for update 。
*/
