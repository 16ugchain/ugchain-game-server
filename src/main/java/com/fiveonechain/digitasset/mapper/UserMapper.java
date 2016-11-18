package com.fiveonechain.digitasset.mapper;

import com.fiveonechain.digitasset.domain.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by yuanshichao on 2016/11/7.
 */

@Mapper
public interface UserMapper {

    final String coloms="user_id,user_name,password,telephone,role,status";
    final String entity="#{user.user_id}, #{user.user_name},#{user.password},#{user.telephone},#{user.role},#{user.status}";

    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "user_name", column = "user_name")
    })
    @Select("SELECT * FROM user WHERE user_id = #{user_id}")
    User findByUserId(Long user_id);

    @Select("SELECT * FROM user WHERE user_name = #{user_name}")
    User getUserByUserName(String user_name);

    @Select("SELECT exists (select user_id FROM user WHERE user_name = #{user_name})")
    boolean isExistsUserName(String user_name);


    @Insert("INSERT INTO user("+coloms+") VALUES("+entity+")")
    int insertUser(@Param("user")User user);

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