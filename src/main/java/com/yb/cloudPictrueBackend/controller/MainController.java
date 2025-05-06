package com.yb.cloudPictrueBackend.controller;

import com.yb.cloudPictrueBackend.common.BaseResponse;
import com.yb.cloudPictrueBackend.common.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {

    /**
     * 健康检查
     */
    @GetMapping("/health")
    public BaseResponse<String> health() {
        return Result.success("ok");
    }
}
