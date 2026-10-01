package com.videoshare.services;

import com.videoshare.models.Video_24110162;
import java.util.List;

public interface IVideoService_24110162 {
    Video_24110162 getVideoDetail(String videoId);
    List<Video_24110162> findVideosByCategoryId(int categoryId, int offset, int limit);
    int countVideosByCategoryId(int categoryId);
}
