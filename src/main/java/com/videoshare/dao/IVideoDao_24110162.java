package com.videoshare.dao;

import com.videoshare.models.Video_24110162;
import java.util.List;

public interface IVideoDao_24110162 extends IBaseDao_24110162<Video_24110162, String> {
    // Lấy video theo danh mục có phân trang (phục vụ Câu 5)
    List<Video_24110162> findVideosByCategoryId(int categoryId, int offset, int limit);
    
    // Đếm số lượng video theo danh mục (phục vụ Câu 6)
    int countVideosByCategoryId(int categoryId);

    // Lấy chi tiết video kèm thông tin category, share, like (phục vụ Câu 4)
    Video_24110162 getVideoDetail(String videoId);
}
