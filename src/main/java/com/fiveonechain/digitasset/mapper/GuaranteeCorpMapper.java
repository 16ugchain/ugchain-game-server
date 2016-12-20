package com.fiveonechain.digitasset.mapper;

import com.fiveonechain.digitasset.domain.GuaranteeCorp;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by yuanshichao on 2016/11/14.
 */
@Mapper
public interface GuaranteeCorpMapper {
    final static String columns = "guaranteecorp_id,user_id,corp_name,juristic_person,main_business,pkcs12,status";
    final static String entity = "#{guaranteeCorp.guaranteecorpId},#{guaranteeCorp.userId},#{guaranteeCorp.corpName},#{guaranteeCorp.juristicPerson},#{guaranteeCorp.mainBusiness},#{guaranteeCorp.pkcs12},#{guaranteeCorp.status}";
    @Insert("insert into guarantee_corp ("+columns+") values("+entity+")")
    int insertCorp(@Param("guaranteeCorp") GuaranteeCorp guaranteeCorp);

    @Select("select exists (select user_id from guarantee_corp where user_id=#{userId})")
    boolean isExists(@Param("userId")int userId);
    @Results(id = "GuaranteeCorp", value = {
            @Result(property = "guaranteecorpId", column = "guaranteecorp_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "corpName", column = "corp_name"),
            @Result(property = "juristicPerson", column = "juristic_person"),
            @Result(property = "mainBusiness", column = "main_business"),
            @Result(property = "createTime", column = "create_time"),
            @Result(property = "updateTime", column = "update_time"),
            @Result(property = "status", column = "status"),
            @Result(property = "pkcs12", column = "pkcs12")
    })
    @Select("select * from guarantee_corp where user_id=#{userId}")
    GuaranteeCorp findByUserId(@Param("userId")int userId);

    @ResultMap("GuaranteeCorp")
    @Select("select * from guarantee_corp where guaranteecorp_id=#{guarId}")
    GuaranteeCorp findByGuarId(@Param("guarId")int guarId);

    @ResultMap("GuaranteeCorp")
    @Select("select guaranteecorp_id,user_id,corp_name,juristic_person,main_business,status from guarantee_corp" )
    List<GuaranteeCorp> findAvalibleList();
}


/*

CREATE TABLE `guarantee_corp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `guaranteecorp_id` int(11) NOT NULL,
  `corp_name` varchar(5) NOT NULL,
  `create_time` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,
  `status` tinyint(4) NOT NULL default '0',
  `juristic_person` varchar(5) NOT NULL,
  `main_business` varchar(10) NOT NULL,

  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_userid` (`user_id`)

) DEFAULT CHARSET=utf8 COLLATE = utf8_bin
*/