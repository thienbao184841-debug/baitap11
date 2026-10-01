package com.videoshare.services.impl;

import com.videoshare.dao.IVideoDao_24110162;
import com.videoshare.dao.impl.VideoDaoImpl_24110162;
import com.videoshare.models.Video_24110162;
import com.videoshare.services.IVideoService_24110162;

public class VideoServiceImpl_24110162 implements IVideoService_24110162 {
    private IVideoDao_24110162 videoDao = new VideoDaoImpl_24110162();

    @Override
    public Video_24110162 getVideoDetail(String videoId) {
        return videoDao.getVideoDetail(videoId);
    }

    @Override
    public java.util.List<Video_24110162> findVideosByCategoryId(int categoryId, int offset, int limit) {
        return videoDao.findVideosByCategoryId(categoryId, offset, limit);
    }

    @Override
    public int countVideosByCategoryId(int categoryId) {
        return videoDao.countVideosByCategoryId(categoryId);
    }
}
