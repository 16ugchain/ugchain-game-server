package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.domain.ImageTypeEnum;
import com.fiveonechain.digitasset.domain.ImageUrl;

import java.util.List;

/**
 * Created by fanjl on 16/11/18.
 */
public interface ImageUrlService {
    int insertImageUrl(ImageUrl imageUrl);

    ImageUrl findByImageId(int imageId);

    List<String> getUrlListByImageIds(List<Integer> imageIds);

    ImageUrl getImageByImageIdAndUserId(int imageId, int userId);

    ImageUrl findByUserIdAndType(int userId,int type);

    boolean isExists(int userId,int type);

    int nextImageUrlId();

    boolean isImageIdsValid(int userId, List<Integer> imageIds, ImageTypeEnum expectedType);

    boolean isImageIdValid(int userId, int imageId, ImageTypeEnum expectedType);
}
