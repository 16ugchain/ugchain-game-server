package com.ugc.micropayment.mapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

/**
 * Created by fanjl on 2017/4/5.
 */
@Mapper
public interface AccountMapper {
	final String columns = "account_id,address,nonce,amount,creat_time,update_time,status";
    final String entity = "#{account.accountId},#{account.address},#{account.nonce}" +
            ",#{account.amount},#{account.creatTime},#{account.updateTime},#{blockRecord.status}";

	
	@Results(id="account",value={
            @Result(property = "accountId", column = "account_id"),
            @Result(property = "address", column = "address"),
            @Result(property = "nonce", column = "nonce"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "status", column = "status")
    })
    @Insert("INSERT INTO account("+columns+") VALUES("+entity+")")
	void insertAccount(String address);

	@Select("SELECT count(1) FROM account WHERE address = #{address}")
	int findAddress(String address);
	
	@Select("SELECT count(1) FROM account WHERE address = #{address} and amount < #{account.amount}")
	int queryAmountEnough(String address, BigDecimal amount);
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
