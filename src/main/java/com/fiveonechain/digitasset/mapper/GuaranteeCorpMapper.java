package com.fiveonechain.digitasset.mapper;

import com.fiveonechain.digitasset.domain.GuaranteeCorp;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by yuanshichao on 2016/11/14.
 */
@Mapper
public interface GuaranteeCorpMapper {
    final static String columns = "user_id,corp_name,juristic_person,main_business,status";
    final static String entity = "#{guaranteeCorp.user_id},#{guaranteeCorp.corp_name},#{guaranteeCorp.juristic_person},#{guaranteeCorp.main_business},#{guaranteeCorp.status}";
    @Insert("insert into guarantee_corp ("+columns+") values("+entity+")")
    int insertCorp(@Param("guaranteeCorp") GuaranteeCorp guaranteeCorp);

    @Select("select exists (select user_id from guarantee_corp where user_id=#{user_id})")
    boolean isExists(@Param("user_id")int user_id);

    @Select("select * from guarantee_corp where user_id=#{user_id}")
    GuaranteeCorp findByUserId(@Param("user_id")int user_id);
}


/*

CREATE TABLE `guarantee_corp` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
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