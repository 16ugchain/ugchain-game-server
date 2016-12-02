package com.fiveonechain.digitasset.mapper;

import com.fiveonechain.digitasset.domain.UserAuth;
import org.apache.ibatis.annotations.*;

/**
 * Created by yuanshichao on 2016/11/10.
 */

@Mapper
public interface UserAuthMapper {
    final String columns = "user_id,real_name,identity,identity_type,email,fixed_line,status";
    final String entity = "#{userAuth.user_id},#{userAuth.real_name},#{userAuth.identity},#{userAuth.identity_type}," +
            "#{userAuth.email},#{userAuth.fixed_line},#{userAuth.status}";
    @Select("SELECT exists (select user_id FROM user_auth WHERE identity = #{identity})")
    boolean isIdentityExists(String id);


    @Select("SELECT * FROM user_auth WHERE user_id = #{user_id}")
    UserAuth findAuthById(Long user_id);

    @Insert("INSERT INTO user_auth("+columns+") VALUES("+entity+")")
    int insertUserAuth(@Param("userAuth") UserAuth userAuths);

    @Select("SELECT exists (select user_id FROM user_auth WHERE user_id = #{user_id})")
    boolean isExistsUserAuth(int user_id);

    @Update("update user_auth set credit_card_id=#{credit_card_id},credit_card_owner=#{credit_card_owner},credit_card_bank=#{credit_card_bank}" +
            "where user_id = #{user_id}")
    boolean bindCreditCard(UserAuth userAuth);


}




/*

CREATE TABLE `user_auth` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `real_name` varchar(5) NOT NULL,
  `identity` char(18) NOT NULL,
  `create_time` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `imageType` tinyint(4) NOT NULL,
  `url` varchar(50),
  `status` tinyint(4) NOT NULL default '0',

  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_userid` (`user_id`)

) DEFAULT CHARSET=utf8 COLLATE = utf8_bin

*/
