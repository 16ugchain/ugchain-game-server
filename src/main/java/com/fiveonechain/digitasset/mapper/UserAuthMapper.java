package com.fiveonechain.digitasset.mapper;

import org.apache.ibatis.annotations.Mapper;

/**
 * Created by yuanshichao on 2016/11/10.
 */

@Mapper
public interface UserAuthMapper {
}

/*

CREATE TABLE `user_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `real_name` varchar(5) NOT NULL,
  `identity` char(18) NOT NULL,
  `create_time` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `update_time ` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL default '0',

  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_userid` (`user_id`),

) DEFAULT CHARSET=utf8 COLLATE = utf8_bin

*/
