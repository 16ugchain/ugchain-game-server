package com.fiveonechain.digitasset.mapper;

import com.fiveonechain.digitasset.domain.Contract;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by yuanshichao on 2016/11/14.
 */
@Mapper
public interface ContractMapper {
    String INSERT_COLUMN = "contract_id, content";

    String INSERT_PROPERTY = "#{contract.contract_id}, #{contract.content}";

    String ALL_COLUMN = "contract_id, content, create_time";
    @Insert("INSERT INTO contract(" + INSERT_COLUMN + ") VALUES (" + INSERT_PROPERTY +")")
    int insert(@Param("contract") Contract contract);

    @Select("select "+ALL_COLUMN+" from contract where contract_id=#{contract_id}")
    Contract findByContractId(@Param("contract_id") int contract_id);
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
