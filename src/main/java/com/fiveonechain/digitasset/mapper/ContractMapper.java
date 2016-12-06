package com.fiveonechain.digitasset.mapper;

import com.fiveonechain.digitasset.domain.Contract;
import org.apache.ibatis.annotations.*;

/**
 * Created by yuanshichao on 2016/11/14.
 */
@Mapper
public interface ContractMapper {
    String INSERT_COLUMN = "contract_id, content";

    String INSERT_PROPERTY = "#{contract.contractId}, #{contract.content}";

    String ALL_COLUMN = "contract_id, content, create_time";
    @Insert("INSERT INTO contract(" + INSERT_COLUMN + ") VALUES (" + INSERT_PROPERTY +")")
    int insert(@Param("contract") Contract contract);

    @Results(id = "contract", value = {
            @Result(property = "contractId", column = "contract_id"),
            @Result(property = "createTime", column = "create_time"),
    })
    @Select("select "+ALL_COLUMN+" from contract where contract_id=#{contractId}")
    Contract findByContractId(@Param("contractId") int contractId);
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
