package com.yb.cloudPictrueBackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yb.cloudPictrueBackend.model.dto.picture.PictureQueryRequest;
import com.yb.cloudPictrueBackend.model.dto.picture.PictureUploadRequest;
import com.yb.cloudPictrueBackend.model.entity.Picture;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yb.cloudPictrueBackend.model.entity.User;
import com.yb.cloudPictrueBackend.model.vo.PictureVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
* @author bryan
* @description 针对表【picture(图片)】的数据库操作Service
* @createDate 2025-05-08 18:50:18
*/
public interface PictureService extends IService<Picture> {

    /**
     * 上传图片
     *
     * @param multipartFile
     * @param pictureUploadRequest
     * @param loginUser
     * @return
     */
    PictureVO uploadPicture(MultipartFile multipartFile,
                            PictureUploadRequest pictureUploadRequest,
                            User loginUser);


    /**
     * 将查询请求转换为 QueryWrapper对象
     * @param pictureQueryRequest
     * @return
     */
    QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);

    PictureVO getPictureVO(Picture picture, HttpServletRequest request);

    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);

    void validPicture(Picture picture);
}
