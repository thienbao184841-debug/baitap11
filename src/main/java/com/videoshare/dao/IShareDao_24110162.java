package com.videoshare.dao;

import com.videoshare.models.Share_24110162;

public interface IShareDao_24110162 extends IBaseDao_24110162<Share_24110162, Integer> {
    // Có thể thêm các hàm đặc thù của Share_24110162
    int countSharesByVideoId(String videoId);
}
