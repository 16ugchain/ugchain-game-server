package com.fiveonechain.digitasset.mapper;

import com.fiveonechain.digitasset.domain.Asset;
import org.apache.ibatis.annotations.*;

/**
 * Created by yuanshichao on 2016/11/14.
 */

@Mapper
public interface AssetMapper {

    String INSERT_COLUMN = "asset_id, user_id, guar_id, name, "
            + "value, description, certificate, photos, "
            + "start_time, end_time, fee, status";

    String INSERT_PROPERTY = "#{assetId}, #{userId}, #{guarId}, #{name}, "
            + "#{value}, #{description}, #{certificate}, #{photos}, "
            + "#{startTime}, #{endTime}, #{fee}, #{status}";

    String ALL_COLUMN = "asset_id, user_id, guar_id, name, "
            + "value, description, certificate, photos, "
            + "start_time, end_time, eval_conclusion, eval_value, "
            + "fee, exp_earnings, status";

    @Insert("INSERT INTO asset(" + INSERT_COLUMN + ") VALUES (" + INSERT_PROPERTY +")")
    int insert(Asset asset);

    @Select("SELECT " + ALL_COLUMN + " FROM asset WHERE asset_id = #{assetId}")
    Asset select(@Param("assetId") int assetId);

    /*
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
    */

}

/*

CREATE TABLE `asset` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `asset_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `guar_id` int(11) NOT NULL,

  `name` varchar(20) CHARACTER SET utf8 NOT NULL,
  `value` int(11) NOT NULL ,
  `description` varchar(200) CHARACTER SET utf8 NOT NULL,
  `certificate` varchar(32) CHARACTER SET latin1 NOT NULL,
  `photos` varchar(192) CHARACTER SET latin1 NOT NULL ,

  `start_time` timestamp NOT NULL,
  `end_time` timestamp NOT NULL,

  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

  `eval_conclusion` varchar(200) CHARACTER SET utf8 NOT NULL DEFAULT '',
  `eval_value` int(11) NOT NULL DEFAULT -1,
  `fee` int(11) NOT NULL DEFAULT -1,
  `exp_earnings` int(11) NOT NULL DEFAULT -1,

  `status` tinyint(4) NOT NULL ,

  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_assetid` (`asset_id`)

)

*/
