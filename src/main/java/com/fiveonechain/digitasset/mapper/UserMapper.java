package com.fiveonechain.digitasset.mapper;

import com.fiveonechain.digitasset.domain.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by yuanshichao on 2016/11/7.
 */

@Mapper
public interface UserMapper {

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name")
    })
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(Long id);


    @Insert("INSERT INTO user(id, name) VALUES(#{id}, #{name})")
    int insertUser(User user);

    @Update("UPDATE user SET name = #{name} WHERE id = #{id}")
    int updateUser(User user);

}

/*

CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `user_name` varchar(16) NOT NULL,
  `password` char(50) NOT NULL,
  `create_time` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `role` tinyint(4) NOT NULL default '0',
  `status` tinyint(4) NOT NULL default '0',
  `telephone` char(20) NOT NULL default '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_userid` (`user_id`)

) DEFAULT CHARSET=utf8 COLLATE = utf8_bin

*/