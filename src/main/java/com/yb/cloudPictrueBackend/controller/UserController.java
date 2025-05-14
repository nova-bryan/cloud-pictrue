package com.yb.cloudPictrueBackend.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yb.cloudPictrueBackend.annotation.AuthCheck;
import com.yb.cloudPictrueBackend.common.BaseResponse;
import com.yb.cloudPictrueBackend.common.DeleteRequest;
import com.yb.cloudPictrueBackend.common.Result;
import com.yb.cloudPictrueBackend.exception.BusinessException;
import com.yb.cloudPictrueBackend.exception.ErrorCode;
import com.yb.cloudPictrueBackend.exception.ThrowUtils;
import com.yb.cloudPictrueBackend.model.dto.user.*;
import com.yb.cloudPictrueBackend.model.entity.User;
import com.yb.cloudPictrueBackend.model.vo.LoginUserVO;
import com.yb.cloudPictrueBackend.model.vo.UserVO;
import com.yb.cloudPictrueBackend.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.yb.cloudPictrueBackend.constant.UserConstant.ADMIN_ROLE;
import static com.yb.cloudPictrueBackend.constant.UserConstant.DEFAULT_PASSWORD;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;
    private Object UserConstant;

    /**
     * 用户注册
     */
    @AuthCheck(mustRole = ADMIN_ROLE)
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        ThrowUtils.throwIf(userRegisterRequest == null, ErrorCode.PARAMS_ERROR);
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return Result.success(result);

    }

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(userLoginRequest == null, ErrorCode.PARAMS_ERROR);
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        LoginUserVO loginUserVO = userService.userLogin(userAccount, userPassword, request);
        return Result.success(loginUserVO);
    }

    /**
     * 获取当前登录用户
     */
    @GetMapping("/get/login")
    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        return Result.success(userService.getLoginUserVO(loginUser));
    }

    /**
     * 用户登录
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR);
        boolean result = userService.userLogout(request);
        return Result.success(result);
    }

    /**
     * 用户创建
     */
    @PostMapping("/add")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<Long> userAdd(@RequestBody UserAddRequest userAddRequest) {
        ThrowUtils.throwIf(ObjUtil.isEmpty(userAddRequest), ErrorCode.PARAMS_ERROR);
        User user = new User();
        String password = userService.getEncryptPassword(DEFAULT_PASSWORD);
        user.setUserPassword(password);
        BeanUtil.copyProperties(userAddRequest, user);
        boolean result = userService.save(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return Result.success(user.getId());
    }

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<User> getUserById(long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        User user = userService.getById(id);
        ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR);
        return Result.success(user);
    }

    /**
     * 根据id获取包装类
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<UserVO> getUserVOById(long id) {
        ThrowUtils.throwIf(id <= 0, ErrorCode.PARAMS_ERROR);
        BaseResponse<User> response = getUserById(id);
        User user = response.getData();
        ThrowUtils.throwIf(user == null, ErrorCode.NOT_FOUND_ERROR);
        return Result.success(userService.getUserVO(user));
    }

    /**
     * 删除用户
     *
     * @param deleteRequest
     * @return
     */
    @PostMapping("/delete")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<Boolean> deleteUser(@RequestBody DeleteRequest deleteRequest) {
        if (deleteRequest == null || deleteRequest.getId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean result = userService.removeById(deleteRequest.getId());
        return Result.success(result);
    }

    /**
     * 更新用户
     */
    @PostMapping("/update")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<Boolean> deleteUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        if (userUpdateRequest == null || userUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = new User();
        BeanUtil.copyProperties(userUpdateRequest, user);
        boolean result = userService.updateById(user);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR);
        return Result.success(result);
    }

    /**
     * 分页获取用户封装类列表
     */
    @PostMapping("/list/page/vo")
    @AuthCheck(mustRole = ADMIN_ROLE)
    public BaseResponse<Page<UserVO>> listUserVoByPage(@RequestBody UserQueryRequest userQueryRequest) {
        ThrowUtils.throwIf(userQueryRequest == null, ErrorCode.PARAMS_ERROR);
        long current = userQueryRequest.getCurrent();
        long pageSize = userQueryRequest.getPageSize();
        Page<User> userPage = userService.page(new Page<>(current, pageSize)
                , userService.getQueryWrapper(userQueryRequest));
        Page<UserVO> userVOPage = new Page<>(current, pageSize, userPage.getTotal());
        List<UserVO> userVOList = userService.getUserVOList(userPage.getRecords());
        userVOPage.setRecords(userVOList);
        return Result.success(userVOPage);
    }
}
