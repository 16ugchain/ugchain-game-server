package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.domain.ImageUrl;

/**
 * Created by fanjl on 16/11/18.
 */
public interface IimageUrlService {
    int insertImageUrl(ImageUrl imageUrl);

    ImageUrl findByImageId(int image_id);

    ImageUrl findByUserIdAndType(int user_id,int type);

    boolean isExists(int user_id,int type);

    int nextImageUrlId();
}
