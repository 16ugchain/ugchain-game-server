package com.fiveonechain.digitasset.mapper;

import com.fiveonechain.digitasset.domain.UserAuth;
import org.apache.ibatis.annotations.*;

/**
 * Created by yuanshichao on 2016/11/10.
 */

@Mapper
public interface UserAuthMapper {
    final String columns = "user_id,real_name,identity,identity_type,email,fixed_line,status";
    final String entity = "#{userAuth.userId},#{userAuth.realName},#{userAuth.identity},#{userAuth.identityType}," +
            "#{userAuth.email},#{userAuth.fixedLine},#{userAuth.status}";
    @Select("SELECT exists (select user_id FROM user_auth WHERE identity = #{identity})")
    boolean isIdentityExists(String id);

    @Results(id="userAuth",value={
            @Result(property = "userId", column = "user_id"),
            @Result(property = "realName", column = "real_name"),
            @Result(property = "identityType", column = "identity_type"),
            @Result(property = "fixedLine", column = "fixed_line"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "creditCardId", column = "credit_card_id"),
            @Result(property = "creditCardOwner", column = "credit_card_owner"),
            @Result(property = "creditCardBank", column = "credit_card_bank"),
    })
    @Select("SELECT * FROM user_auth WHERE user_id = #{userId}")
    UserAuth findAuthById(int userId);

    @Insert("INSERT INTO user_auth("+columns+") VALUES("+entity+")")
    int insertUserAuth(@Param("userAuth") UserAuth userAuths);

    @Select("SELECT exists (select user_id FROM user_auth WHERE user_id = #{userId})")
    boolean isExistsUserAuth(int userId);

    @Update("update user_auth set credit_card_id=#{creditCardId},credit_card_owner=#{creditCardOwner},credit_card_bank=#{creditCardBank}" +
            "where user_id = #{userId}")
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
