package com.fiveonechain.digitasset.mapper;

/**
 * Created by fanjl on 2017/4/6.
 */
public interface BlockRecordMapper {
}

/*
充值提现表
CREATE TABLE `block_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `block_record_id` int(11) NOT NULL ,
  `transaction_id` varchar(50)  ,
  `target_address` varchar(50) NOT NULL,
  `amount` decimal(10,5) NOT NULL,
  `fee` decimal(10,5) NOT NULL,
  `nonce` int(11) NOT NULL,
  `type` tinyint(4) NOT NULL,
  `create_time` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL default '0',
  PRIMARY KEY (`id`),
) DEFAULT CHARSET=utf8 COLLATE = utf8_bin
//使用transcational 和 for update 保证并发情况下nonce的一致性。充值以及提现必须先从accout查询nonce和amount
//充值或者提现后异步从区块链查询交易信息，能查到后更新transaction_id,status.
//fee：手续费
//status: 0处理中，1成功，2失败。
//type: 0充值 ， 1提现

*/
