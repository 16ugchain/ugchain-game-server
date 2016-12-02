package com.fiveonechain.digitasset.mapper;

import com.fiveonechain.digitasset.domain.ImageUrl;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * Created by fanjl on 16/11/18.
 */
@Mapper
public interface ImageUrlMapper {
    static final String columns = "image_id,user_id,url,type";
    static final String allcolumns = "id,image_id,user_id,url,type,create_time";
    static final String entity = "#{imageUrl.image_id},#{imageUrl.user_id},#{imageUrl.url},#{imageUrl.type}";
    @Insert("insert into image_url ("+columns+") values("+entity+")")
    int insertImageUrl(@Param("imageUrl") ImageUrl imageUrl);

    @Select("select "+allcolumns+" from image_url where image_id=#{image_id}")
    ImageUrl getByImageId(@Param("image_id")int image_id);

    @Select("SELECT " + allcolumns + " FROM image_url WHERE image_id=#{image_id} AND user_id=#{userId}")
    ImageUrl selectByImageIdAndUserId(
            @Param("imageId") int imageId,
            @Param("userId") int userId);

    @Select("select "+allcolumns+" from image_url where user_id=#{image_id} and type=#{type}")
    ImageUrl findByUserIdAndType(@Param("user_id")int user_id,@Param("type")int type);

    @Select("select exists (select "+allcolumns+" from image_url where user_id=#{user_id} and type=#{type})")
    boolean isExists(@Param("user_id")int user_id,@Param("type")int type);


}


/*

CREATE TABLE `image_url` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `image_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `url` varchar(50) NOT NULL,
  `type` tinyint(4) NOT NULL,
  `create_time` timestamp NOT NULL default CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP,

  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_imageid` (`user_id`,`image_id`)

) DEFAULT CHARSET=utf8 COLLATE = utf8_bin

*/
