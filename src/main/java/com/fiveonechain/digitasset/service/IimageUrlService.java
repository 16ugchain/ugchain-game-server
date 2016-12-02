package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.domain.ImageTypeEnum;
import com.fiveonechain.digitasset.domain.ImageUrl;

import java.util.List;

/**
 * Created by fanjl on 16/11/18.
 */
public interface IimageUrlService {
    int insertImageUrl(ImageUrl imageUrl);

    ImageUrl findByImageId(int image_id);

    ImageUrl getImageByImageIdAndUserId(int imageId, int userId);

    ImageUrl findByUserIdAndType(int user_id,int type);

    boolean isExists(int user_id,int type);

    int nextImageUrlId();

    boolean isImageIdsValid(int userId, List<Integer> imageIds, ImageTypeEnum expectedType);

    boolean isImageIdValid(int userId, int imageId, ImageTypeEnum expectedType);
}
