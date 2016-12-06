package com.fiveonechain.digitasset.mapper;

import com.fiveonechain.digitasset.domain.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by yuanshichao on 2016/11/7.
 */

@Mapper
public interface UserMapper {

    final String coloms = "user_id,user_name,password,role,status";
    final String entity = "#{user.userId}, #{user.userName},#{user.password},#{user.role},#{user.status}";

    @Results(id = "user", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "userName", column = "user_name"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time")
    })
    @Select("SELECT * FROM user WHERE user_id = #{user_id}")
    User findByUserId(int user_id);

    @ResultMap("user")
    @Select("SELECT * FROM user WHERE user_name = #{userName}")
    User getUserByUserName(String userName);

    @Select("SELECT exists (select user_id FROM user WHERE user_name = #{userName})")
    boolean isExistsUserName(String userName);

    @Select("SELECT exists (select telephone FROM user WHERE telephone = #{telephone})")
    boolean isExistsTelephone(String telephone);

    @Insert("INSERT INTO user(" + coloms + ") VALUES(" + entity + ")")
    int insertUser(@Param("user") User user);

    @Update("UPDATE user SET user_name = #{userName} WHERE user_id = #{userId}")
    int updateUser(User user);

    @Update("UPDATE user SET telephone = #{user.telephone} WHERE user_id = #{user.userId}")
    boolean updateMobile(@Param("user") User user);

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