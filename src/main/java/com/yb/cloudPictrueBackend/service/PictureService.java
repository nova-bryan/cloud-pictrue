package com.yb.cloudPictrueBackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yb.cloudPictrueBackend.common.DeleteRequest;
import com.yb.cloudPictrueBackend.model.dto.picture.PictureQueryRequest;
import com.yb.cloudPictrueBackend.model.dto.picture.PictureReviewRequest;
import com.yb.cloudPictrueBackend.model.dto.picture.PictureUploadByBatchRequest;
import com.yb.cloudPictrueBackend.model.dto.picture.PictureUploadRequest;
import com.yb.cloudPictrueBackend.model.entity.Picture;
import com.yb.cloudPictrueBackend.model.entity.User;
import com.yb.cloudPictrueBackend.model.vo.PictureVO;

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
     * @param inputSource
     * @param pictureUploadRequest
     * @param loginUser
     * @return
     */
    PictureVO uploadPicture(Object inputSource,
                            PictureUploadRequest pictureUploadRequest,
                            User loginUser);


    /**
     * 将查询请求转换为 QueryWrapper对象
     * @param pictureQueryRequest
     * @return
     */
    QueryWrapper<Picture> getQueryWrapper(PictureQueryRequest pictureQueryRequest);

    /**
     * 获取单个图片封装
     * @param picture
     * @param request
     * @return
     */
    PictureVO getPictureVO(Picture picture, HttpServletRequest request);

    /**
     *
     * @param picturePage
     * @param request
     * @return
     */
    Page<PictureVO> getPictureVOPage(Page<Picture> picturePage, HttpServletRequest request);

    /**
     * 分页获取图片封装
     */
    void validPicture(Picture picture);

    /**
     * 图片审核
     * @param pictureReviewRequest
     * @param loginUser
     */
    void doPictureReview(PictureReviewRequest pictureReviewRequest,User loginUser);

    /**
     * 填充审核参数
     * @param picture
     * @param loginUser
     */
    public void fileReviewParams(Picture picture,User loginUser);

    /**
     * 批量抓取和创建图片
     *
     * @param pictureUploadByBatchRequest
     * @param loginUser
     * @return 成功创建的图片数
     */
    Integer uploadPictureByBatch(
            PictureUploadByBatchRequest pictureUploadByBatchRequest,
            User loginUser
    );

    /**
     * 删除COS对象存储中的图片
     * @param deleteRequest
     */
    void deleteCos(DeleteRequest deleteRequest);
}
