package com.fiveonechain.digitasset.service;

import com.fiveonechain.digitasset.domain.ImageTypeEnum;
import com.fiveonechain.digitasset.domain.ImageUrl;
import com.fiveonechain.digitasset.mapper.ImageUrlMapper;
import com.fiveonechain.digitasset.mapper.SequenceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by fanjl on 16/11/18.
 */
@Component
public class ImageUrlServiceImpl implements IimageUrlService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUrlServiceImpl.class);


    @Autowired
    private SequenceMapper sequenceMapper;

    @Autowired
    private ImageUrlMapper imageUrlMapper;

    @Override
    public int insertImageUrl(ImageUrl imageUrl) {
        imageUrl.setImage_id(nextImageUrlId());
        return imageUrlMapper.insertImageUrl(imageUrl);
    }

    @Override
    public ImageUrl findByImageId(int image_id) {
        return imageUrlMapper.getByImageId(image_id);
    }

    @Override
    public ImageUrl getImageByImageIdAndUserId(int imageId, int userId) {
        return imageUrlMapper.selectByImageIdAndUserId(imageId, userId);
    }

    @Override
    public boolean isImageIdValid(int userId, int imageId, ImageTypeEnum expectedType) {
        ImageUrl image = imageUrlMapper.selectByImageIdAndUserId(imageId, userId);
        if (image != null && image.getType() == expectedType.getId()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isImageIdsValid(int userId, List<Integer> imageIds, ImageTypeEnum expectedType) {
        for (int id : imageIds) {
            if (!isImageIdValid(userId, id, expectedType)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ImageUrl findByUserIdAndType(int user_id, int type) {
        return imageUrlMapper.findByUserIdAndType(user_id,type);
    }

    @Override
    public boolean isExists(int user_id, int type) {
        return imageUrlMapper.isExists(user_id,type);
    }

    @Override
    public int nextImageUrlId() {
        return sequenceMapper.nextId(SequenceMapper.IMAGE_URL);
    }
}
