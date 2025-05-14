package com.yb.cloudPictrueBackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yb.cloudPictrueBackend.model.dto.user.UserQueryRequest;
import com.yb.cloudPictrueBackend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yb.cloudPictrueBackend.model.vo.LoginUserVO;
import com.yb.cloudPictrueBackend.model.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author bryan
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-05-07 13:30:34
*/
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userAccount
     * @param userPassword
     * @param checkPassword
     * @return
     */
    long userRegister(String userAccount, String userPassword, String checkPassword);
    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param request
     * @return 脱敏后的用户信息
     */
    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 获取脱敏的已登录用户信息
     *
     * @return  返回脱敏的用户信息
     */
    LoginUserVO getLoginUserVO(User user);

    /**
     * 获取登录用户
     */
    User getLoginUser(HttpServletRequest request);

    /**
     * 用户注销
     */
    boolean userLogout(HttpServletRequest request);

    /**
     * 获取脱敏后的单个用户信息
     * @param user
     * @return
     */
    UserVO getUserVO(User user);

    /**
     * 获取脱敏后的用户列表
     * @param userList
     * @return
     */
    List<UserVO> getUserVOList(List<User> userList);

    /**
     * 将查询请求转换为QueryWrapper对象
     * @param userQueryRequest
     * @return
     */
    QueryWrapper<User> getQueryWrapper(UserQueryRequest userQueryRequest);

    /**
     * 将用户密码加密
     * @param userPassword
     * @return
     */
    public String getEncryptPassword(String userPassword);

    /**
     * 是否为管理员
     *
     * @param user
     * @return
     */
    boolean isAdmin(User user);

}
