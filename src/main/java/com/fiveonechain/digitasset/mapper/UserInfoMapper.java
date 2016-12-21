package com.fiveonechain.digitasset.mapper;

import com.fiveonechain.digitasset.domain.UserInfo;
import org.apache.ibatis.annotations.*;

/**
 * Created by yuanshichao on 2016/11/10.
 */

@Mapper
public interface UserInfoMapper {
    final String columns = "user_id,real_name,identity,identity_type,email,fixed_line,status,image_id";
    final String entity = "#{userInfo.userId},#{userInfo.realName},#{userInfo.identity},#{userInfo.identityType}," +
            "#{userInfo.email},#{userInfo.fixedLine},#{userInfo.status},#{userInfo.imageId}";
    @Select("SELECT exists (select user_id FROM user_info WHERE identity = #{identity})")
    boolean isIdentityExists(String id);

    @Results(id="userInfo",value={
            @Result(property = "userId", column = "user_id"),
            @Result(property = "realName", column = "real_name"),
            @Result(property = "identityType", column = "identity_type"),
            @Result(property = "fixedLine", column = "fixed_line"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "creditCardId", column = "credit_card_id"),
            @Result(property = "creditCardOwner", column = "credit_card_owner"),
            @Result(property = "creditCardBank", column = "credit_card_bank"),
            @Result(property = "imageId", column = "image_id" ),
            @Result(property = "iconId", column = "icon_id" )
    })
    @Select("SELECT * FROM user_info WHERE user_id = #{userId}")
    UserInfo findUserInfoByUserId(int userId);

    @Insert("INSERT INTO user_info("+columns+") VALUES("+entity+")")
    int insertUserAuth(@Param("userInfo") UserInfo userInfo);

    @Select("SELECT exists (select user_id FROM user_info WHERE user_id = #{userId})")
    boolean isExistsUserAuth(int userId);

    @Update("update user_info set credit_card_id=#{creditCardId},credit_card_owner=#{creditCardOwner},credit_card_bank=#{creditCardBank}" +
            "where user_id = #{userId}")
    boolean bindCreditCard(UserInfo userInfo);

    @Update("update user_info set icon_id=#{imgId} where user_id = #{userId}")
    boolean updateUserIcon(@Param("imgId") int imgId,@Param("userId") int userId);

}




/*

CREATE TABLE `user_info` (
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
