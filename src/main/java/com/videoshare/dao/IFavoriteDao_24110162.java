package com.videoshare.dao;

import com.videoshare.models.Favorite_24110162;
import java.util.List;

public interface IFavoriteDao_24110162 extends IBaseDao_24110162<Favorite_24110162, Integer> {
    // Có thể thêm các hàm đặc thù của Favorite_24110162
    Favorite_24110162 findByUsernameAndVideoId(String username, String videoId);
    int countFavoritesByVideoId(String videoId);
}
